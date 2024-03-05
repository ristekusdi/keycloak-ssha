package io.github.ristekusdi;

import org.keycloak.Config;
import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.credential.hash.PasswordHashProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SSHAHashProviderFactory implements PasswordHashProviderFactory {
    public static final String ID = "ssha";


    @Override
    public PasswordHashProvider create(KeycloakSession keycloakSession) {
        return new SSHAHashProvider(getId());
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return ID;
    }
}
