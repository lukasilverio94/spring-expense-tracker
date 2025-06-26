package com.dev.expensetracker.service;

import com.dev.expensetracker.domain.entity.AppUser;
import com.dev.expensetracker.exception.AuthenticationException;
import com.dev.expensetracker.repository.AppUserRepository;
import com.dev.expensetracker.security.JwtService;
import com.dev.expensetracker.security.UserAuthenticated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthenticationService(AppUserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String authenticateUser(@NotBlank String email, @NotNull String password) {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Invalid credentials") {
                });

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Invalid credentials");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserAuthenticated(user), null, List.of(() -> "read")
        );

        return jwtService.generateToken(authentication);
    }
}
