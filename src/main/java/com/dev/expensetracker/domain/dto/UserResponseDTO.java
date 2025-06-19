package com.dev.expensetracker.domain.dto;

import java.util.UUID;

public record UserResponseDTO(
        UUID userId,
        String email
) {}
