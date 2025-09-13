/*
 * Rest controller responsible for handling authentication endpoints (Login & Register).
 */

package com.aaronjosh.real_estate_app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaronjosh.real_estate_app.dto.LoginDto;
import com.aaronjosh.real_estate_app.dto.RegisterDto;
import com.aaronjosh.real_estate_app.exceptions.EmailAlreadyExistsException;
import com.aaronjosh.real_estate_app.exceptions.PasswordNotMatchException;
import com.aaronjosh.real_estate_app.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    /*
     * Handles login request and returns JWT if credentials are valid.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto request) {
        try {
            String token = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    /*
     * Handle the registration request and create new user account.
     */

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto userDto) {
        try {
            authService.register(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created an account.");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (PasswordNotMatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
