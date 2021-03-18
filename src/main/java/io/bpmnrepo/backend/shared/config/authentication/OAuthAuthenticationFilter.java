package io.bpmnrepo.backend.shared.config.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!no-security")
public class OAuthAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    @Value("${bpmnrepo.security.usercontext.email_claim}")
    private String emailClaimKey;

    protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final FilterChain filterChain) throws ServletException, IOException {
        final String oAuthKey = this.getOAuthTokenFromHeader(httpServletRequest);
        this.authenticateUserIfOAuthKeyExists(oAuthKey);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /* --------------------------------------- private helper methods --------------------------------------- */

    private String getOAuthTokenFromHeader(final HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }


    private void authenticateUserIfOAuthKeyExists(final String oAuthKey) {
        if (oAuthKey != null) {
            final Jwt decodedToken = this.getOAuthTokenIfTokenIsValid(oAuthKey);
            this.authenticateUser(decodedToken);
        }
    }

    private void authenticateUser(final Jwt oAuthToken) {
        final String username = oAuthToken.getClaimAsString("email");
        final UserToken authenticatedUser = new UserToken(username, AuthorityUtils.NO_AUTHORITIES);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }


    private Jwt getOAuthTokenIfTokenIsValid(final String oAuthToken) {
        final String tokenWithoutBearer = oAuthToken.substring(7);
        return this.jwtDecoder.decode(tokenWithoutBearer);
    }
}
