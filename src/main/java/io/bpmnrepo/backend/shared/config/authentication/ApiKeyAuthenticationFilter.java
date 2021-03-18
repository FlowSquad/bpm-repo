package io.bpmnrepo.backend.shared.config.authentication;

import io.bpmnrepo.backend.shared.exception.AccessRightException;
import io.bpmnrepo.backend.user.infrastructure.entity.UserEntity;
import io.bpmnrepo.backend.user.infrastructure.repository.UserJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Profile("!no-security")
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final UserJpa userJpa;



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String apiKey = httpServletRequest.getHeader("apikey");
        if(apiKey != null){
            checkIfUserExists(apiKey);
            authenticateUser(apiKey);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    private void authenticateUser(String apiKey) {
        final UserEntity userEntity = this.getUserByApiKey(apiKey);
        UserToken userToken = new UserToken(userEntity.getUserName(), AuthorityUtils.NO_AUTHORITIES);
        SecurityContextHolder.getContext().setAuthentication(userToken);
    }


    private void checkIfUserExists(String apiKey) {
        UserEntity userEntity = this.getUserByApiKey(apiKey);
        if(userEntity == null){
            throw new AccessRightException("wrong API key");
        }
    }


    private UserEntity getUserByApiKey(String apiKey){
        UserEntity userEntity = this.userJpa.findByApiKey(apiKey);
        return userEntity;
    }
}
