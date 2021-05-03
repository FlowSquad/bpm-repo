package io.bpmnrepo.backend.shared.config;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("no-security")
public class NoSecurityUserContext implements UserContext {

    private final static String DEFAULT_USER = "dennis.mayer@flowsquad.io";

    @Override
    public String getUserEmail() {
        return DEFAULT_USER;
    }
}
