package com.dev.expensetracker.domain.dto;

public record UserRequestDTO(
        String email,
        String password
) {}
