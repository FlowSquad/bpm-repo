package io.bpmnrepo.backend.shared.config;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("no-security")
public class NoSecurityUserContext implements UserContext {

    private final static String DEFAULT_USER = "NO_SECURITY_USER";

    @Override
    public String getUserEmail() {
        return DEFAULT_USER;
    }
}
