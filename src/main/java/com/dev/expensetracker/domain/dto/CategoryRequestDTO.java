package com.dev.expensetracker.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CategoryRequestDTO(
        @NotBlank(message = "Name can't be empty")
        String name,
        @NotNull(message = "User ID is required")
        UUID userId
    ){
}
