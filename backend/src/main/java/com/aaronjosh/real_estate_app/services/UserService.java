package com.aaronjosh.real_estate_app.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aaronjosh.real_estate_app.models.UserEntity;

@Service
public class UserService {

    public UserEntity getUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = (UserEntity) authentication.getPrincipal();

        // String jwt = (String) authentication.getCredentials();

        return user;
    }

}