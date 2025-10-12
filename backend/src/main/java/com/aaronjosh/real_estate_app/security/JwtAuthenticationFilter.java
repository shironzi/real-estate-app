package com.aaronjosh.real_estate_app.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aaronjosh.real_estate_app.models.UserEntity;
import com.aaronjosh.real_estate_app.repositories.UserRepository;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    protected List<String> publicUrls = new ArrayList<>(List.of("/api/property/", "/api/image/"));

    protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String path = req.getServletPath();
            String method = req.getMethod();

            System.out.println(path);

            if (path.equals("/api/property/") && method.equalsIgnoreCase("GET")) {
                filterChain.doFilter(req, res);
                return;
            }

            if (path.startsWith("/api/image/")) {
                filterChain.doFilter(req, res);
                return;
            }

            if (path.equals("/api/auth/register") || path.equals("/api/auth/login")) {
                filterChain.doFilter(req, res);
                return;
            }

            final String authHeader = req.getHeader("Authorization");

            System.out.println(authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new InsufficientAuthenticationException("Missing or invalid Authorization header");
            }

            final String jwt = authHeader.substring(7);

            if (jwtService.isBlacklisted(jwt)) {
                throw new BadCredentialsException("Token is blacklisted");
            }

            final String email = jwtService.extractEmail(jwt);

            System.out.println(email);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserEntity userEntity = userRepository.findByEmail(email)
                        .orElseThrow(() -> new BadCredentialsException(email));

                if (jwtService.isTokenValid(jwt, userEntity)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userEntity, null, userEntity.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }

            filterChain.doFilter(req, res);
        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }
    }

}
