package utils;

import org.jboss.logging.Logger;

public class LogMe {
    public static void echo(Logger logger, String msg) {
        logger.info("Nea Keycloak Extensions" + " ->> " + msg + " <<-");
    }
}
