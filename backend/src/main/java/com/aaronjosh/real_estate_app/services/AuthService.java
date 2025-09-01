package com.aaronjosh.real_estate_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aaronjosh.real_estate_app.models.UserEntity;
import com.aaronjosh.real_estate_app.repositories.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity register(UserEntity user) {

        // checking if the email is already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // hashing the password with the bcrypt
        String newPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(newPassword);

        return userRepository.save(user);
    }

    public UserEntity login(String email, String password) {

        // checks if email exists
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password."));

        // checks password if valid
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password.");
        }

        return user;
    }
}
