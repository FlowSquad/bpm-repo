package io.bpmnrepo.backend.shared.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@Profile("!no-security")
public class BpmnRepoUserContext implements UserContext {

    @Override
    public String getUserEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
