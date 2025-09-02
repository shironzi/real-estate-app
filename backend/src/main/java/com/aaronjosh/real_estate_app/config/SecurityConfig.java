package com.aaronjosh.real_estate_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
    // using bcrypt for the password hashing
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // removing csrf for rest api
                .csrf(csrf -> csrf.disable())

                // handling the session validity
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // only the unauthentication users to access login page
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").anonymous()
                        .anyRequest().authenticated())

                // disable basic auth and default form login
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())

                // exception handling with the bad credentials
                .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, authException) -> {
                    res.setContentType("application/json");

                    if (authException instanceof BadCredentialsException) {
                        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        res.getWriter().write("""
                                    {"error":"Forbidden","message":"Invalid credentials"}
                                """);
                    } else if (authException instanceof InsufficientAuthenticationException) {
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        res.getWriter().write("""
                                    {"error":"Unauthorized","message":"Authentication required"}
                                """);
                    } else {
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        res.getWriter().write("""
                                    {"error":"Unauthorized","message":"Authentication failed"}
                                """);
                    }
                }));

        return http.build();
    }
}
