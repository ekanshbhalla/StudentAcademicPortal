package com.klu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtFilter jwtFilter(JwtUtil jwtUtil) {

        return new JwtFilter(jwtUtil);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtFilter jwtFilter
    ) throws Exception {

        http

            // Disable CSRF
            .csrf(csrf -> csrf.disable())

            // Stateless Session for JWT
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )

            // API Permissions
            .authorizeHttpRequests(auth -> auth

                    // Public APIs
                    .requestMatchers(
    "/auth/**",
    "/health",
    "/swagger-ui/**",
    "/v3/api-docs/**"
).permitAll()

                    .requestMatchers(
                            HttpMethod.GET,
                            "/students/**",
                            "/courses/**",
                            "/enrollments/**",
                            "/prerequisites/**"
                    ).authenticated()

                    .requestMatchers(
                            HttpMethod.POST,
                            "/students/**",
                            "/courses/**",
                            "/enrollments/**",
                            "/prerequisites/**"
                    ).hasRole("ADMIN")

                    .anyRequest().authenticated()
            )

            // Add JWT Filter
            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
