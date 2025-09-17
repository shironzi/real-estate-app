package com.aaronjosh.real_estate_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aaronjosh.real_estate_app.models.UserEntity.Role;
import com.aaronjosh.real_estate_app.security.JwtService;

@Service
public class UserService {
    @Autowired
    private JwtService jwtService;

    private String getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String jwt = (String) authentication.getCredentials();

        return jwt;
    }

    public Long getUserId() {
        return jwtService.extractUserId(getJwtToken());
    }

    public Role getRole() {
        return jwtService.extractRole(getJwtToken());
    }

}