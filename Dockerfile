FROM jboss/keycloak:9.0.0

ENV KEYCLOAK_USER admin
ENV KEYCLOAK_PASSWORD secret

COPY --chown=1000:1000 target/nea-keycloak-extensions.jar /opt/jboss/keycloak/providers/nea-keycloak-extensions.jar
RUN chown -R jboss:jboss /opt/jboss/keycloak
RUN chmod -R g+rw /opt/jboss/keycloak

ENTRYPOINT [ "/opt/jboss/tools/docker-entrypoint.sh" ]
CMD ["-b", "0.0.0.0"]
