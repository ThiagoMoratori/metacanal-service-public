package br.com.metacanal.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

//Reference: https://developer.okta.com/blog/2020/08/07/spring-boot-remote-vs-local-tokens
public class RequestMatchingAuthenticationManagerResolver implements AuthenticationManagerResolver<HttpServletRequest> {

    private final LinkedHashMap<RequestMatcher, AuthenticationManager> authenticationManagers;

    private AuthenticationManager defaultAuthenticationManager = authentication -> {
        throw new AuthenticationServiceException("Cannot authenticate " + authentication);
    };

    public RequestMatchingAuthenticationManagerResolver
            (LinkedHashMap<RequestMatcher, AuthenticationManager> authenticationManagers) {
        Assert.notEmpty(authenticationManagers, "authenticationManagers cannot be empty");
        this.authenticationManagers = authenticationManagers;
    }

    @Override
    public AuthenticationManager resolve(HttpServletRequest context) {
        for (Map.Entry<RequestMatcher, AuthenticationManager> entry : this.authenticationManagers.entrySet()) {
            if (entry.getKey().matches(context)) {
                return entry.getValue();
            }
        }

        return this.defaultAuthenticationManager;
    }

    public void setDefaultAuthenticationManager(AuthenticationManager defaultAuthenticationManager) {
        Assert.notNull(defaultAuthenticationManager, "defaultAuthenticationManager cannot be null");
        this.defaultAuthenticationManager = defaultAuthenticationManager;
    }
}