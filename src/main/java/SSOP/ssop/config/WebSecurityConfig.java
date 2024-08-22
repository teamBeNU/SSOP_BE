/*
package SSOP.ssop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console
                                .anyRequest().authenticated() // Require authentication for other requests
                )
                .headers(headers -> headers
                        .httpStrictTransportSecurity(hsts -> hsts.disable()) // Configure HSTS as needed
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Adjust as needed
                );
        return http.build();
    }
}*/
