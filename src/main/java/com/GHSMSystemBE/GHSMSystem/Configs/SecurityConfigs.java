package com.GHSMSystemBE.GHSMSystem.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for H2 console and API endpoints
                .csrf(csrf -> csrf.disable())

                // Allow frames for H2 console
                .headers(headers -> headers.frameOptions().disable())

                .authorizeHttpRequests(auth -> auth
                        // Allow H2 console access
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        // Allow Swagger access
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Allow your API endpoints
                        .requestMatchers("/api/**").permitAll()
                        // Optional: require authentication for other endpoints
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}