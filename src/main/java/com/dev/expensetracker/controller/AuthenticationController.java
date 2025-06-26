package com.dev.expensetracker.controller;

import com.dev.expensetracker.domain.dto.LoginRequestDTO;
import com.dev.expensetracker.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        String token = authenticationService.authenticateUser(loginRequest.email(), loginRequest.password());
        return ResponseEntity.ok(Map.of("accessToken", token));
    }
}
