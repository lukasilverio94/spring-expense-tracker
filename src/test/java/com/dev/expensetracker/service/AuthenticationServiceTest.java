package com.dev.expensetracker.service;

import com.dev.expensetracker.domain.entity.AppUser;
import com.dev.expensetracker.exception.AuthenticationException;
import com.dev.expensetracker.repository.AppUserRepository;
import com.dev.expensetracker.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    private AppUserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setup() {
        userRepository = mock(AppUserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        authenticationService = new AuthenticationService(userRepository, passwordEncoder, jwtService);
    }

    @Test
    void shouldReturnTokenWhenUserCredentialsAreValid() {
        String email = "user@example.com";
        String password = "password123";
        String encodedPassword = "encodedPassword123";
        AppUser user = new AppUser();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtService.generateToken(any())).thenReturn("fake-jwt-token");

        // when
        String token = authenticationService.authenticateUser(email, password);

        assertEquals("fake-jwt-token", token);
        verify(jwtService).generateToken(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNotFound() {
        String email = "notfound@example.com";
        String password = "anyPassword";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Then
        assertThrows(AuthenticationException.class, () -> {
            authenticationService.authenticateUser(email, password);
        });

        verify(userRepository).findByEmail(email);
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(jwtService);
    }

}
