package com.github.neaplus;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class AnalyticsRequiredActionFactory implements RequiredActionFactory {

    public static final String PROVIDER_ID = "user-login-analytics-action";
    public static final String PROVIDER_NAME = "User Login Analytics";

    @Override
    public RequiredActionProvider create(final KeycloakSession session) {
        return new AnalyticsRequiredActionProvider();
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(final KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayText() {
        return PROVIDER_NAME;
    }
}
