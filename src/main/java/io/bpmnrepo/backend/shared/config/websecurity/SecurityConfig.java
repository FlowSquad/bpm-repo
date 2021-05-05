package io.bpmnrepo.backend.shared.config.websecurity;

import io.bpmnrepo.backend.shared.config.authentication.ApiKeyAuthenticationFilter;
import io.bpmnrepo.backend.shared.config.authentication.OAuthAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Profile("!no-security")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ApiKeyAuthenticationFilter apiKeyAuthenticationFilter;
    private final OAuthAuthenticationFilter oAuthFilter;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        //TODO: safe to do this?
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Unsecured Endpoints
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/user/create").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**"
                ).permitAll();

        // Endpoints that are accessible with API-Key and oAuth-Key
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").authenticated()
                .and()
                .addFilterBefore(this.apiKeyAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(this.oAuthFilter, BasicAuthenticationFilter.class);


        // Endpoints that are only accessible with an oAuth-Key
        http.authorizeRequests().anyRequest().authenticated()
                .and().addFilterBefore(this.oAuthFilter, BasicAuthenticationFilter.class)
                .csrf().ignoringAntMatchers("/api/**", "/actuator/health");


        // TODO: implement strategy to re-enable csrf
        http.csrf().disable();
       // http.cors().disable();
    }

}
