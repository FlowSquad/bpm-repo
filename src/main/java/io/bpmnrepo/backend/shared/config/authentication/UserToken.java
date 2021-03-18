package io.bpmnrepo.backend.shared.config.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserToken extends AbstractAuthenticationToken {

    private final String username;

    public UserToken(final String username, final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }
}
