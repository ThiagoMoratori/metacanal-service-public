package br.com.metacanal.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Reference: https://developer.okta.com/blog/2020/08/07/spring-boot-remote-vs-local-tokens
@Configuration
public class SecurityConfig {

    @Bean
    OAuth2ClientProperties oAuth2ClientProperties() {
        return new OAuth2ClientProperties();
    }
}
