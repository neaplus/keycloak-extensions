package com.github.neaplus;

import org.jboss.logging.Logger;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.UserModel;
import utils.LogMe;

import java.util.*;

public class AnalyticsRequiredActionProvider implements RequiredActionProvider {

    private static final Logger LOG = Logger.getLogger(AnalyticsRequiredActionProvider.class);

    private static final String FIRST_LOGIN = "analytics.first_login";
    private static final String LAST_LOGIN = "analytics.last_login";
    private static final String TOTAL_LOGIN = "analytics.total_login";
    private static final String LOGIN_SOURCE = "analytics.login_source";
    private static final String CLIENTS = "clients";

    @Override
    public void close() {
    }

    @Override
    public void evaluateTriggers(RequiredActionContext context) {

        UserModel user = context.getUser();
        String now = String.valueOf(System.currentTimeMillis());
        LogMe.echo(LOG, String.format("Analytics Check: %s", context.getUser().getId()));

        List<String> firstLogin = user.getAttribute(FIRST_LOGIN);
        if (firstLogin == null || firstLogin.isEmpty()) {
            user.setAttribute(FIRST_LOGIN, Collections.singletonList(now));
        }

        user.setAttribute(LAST_LOGIN, Collections.singletonList(now));

        List<String> totalLogin = user.getAttribute(TOTAL_LOGIN);
        if (totalLogin == null || totalLogin.isEmpty()) {
            totalLogin = Collections.singletonList("1");
        } else {
            totalLogin = Collections.singletonList(String.valueOf(Integer.parseInt(totalLogin.get(0)) + 1));
        }
        user.setAttribute(TOTAL_LOGIN, totalLogin);

        String source = context.getSession().getContext().getClient().getClientId();
        List<String> sources = user.getAttribute(LOGIN_SOURCE);
        if (sources == null || sources.isEmpty()) {
            user.setAttribute(LOGIN_SOURCE, Collections.singletonList(source));
        } else {
            if (!sources.contains(source)) {
                sources.add(source);
                user.setAttribute(LOGIN_SOURCE, sources);
            }
        }

        // clients csv
        List<String> clients = user.getAttribute(CLIENTS);
        if (clients == null || clients.isEmpty() || clients.size() == 0) {
            user.setAttribute(CLIENTS, Collections.singletonList(source));
        } else {
            clients = new ArrayList<String>(Arrays.asList(clients.get(0).split(",")));
            if (!clients.contains(source)) {
                clients.add(source);
                clients = new ArrayList<>(new LinkedHashSet<>(clients));
                user.setAttribute(CLIENTS, Collections.singletonList(String.join(",", clients)));
            }
            // user.setAttribute(LOGIN_SOURCE, clients);
        }

        if (user.getRequiredActions().contains(AnalyticsRequiredActionFactory.PROVIDER_ID)) {
            user.removeRequiredAction(AnalyticsRequiredActionFactory.PROVIDER_ID);
        }
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
    }

    @Override
    public void processAction(RequiredActionContext context) {
        context.success();
    }
}
