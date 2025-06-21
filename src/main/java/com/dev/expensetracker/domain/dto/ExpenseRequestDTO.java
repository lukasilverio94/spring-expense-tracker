package com.dev.expensetracker.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExpenseRequestDTO(
        @NotBlank(message = "Description can't be blank")
        String description,
        LocalDateTime dateTime,
        @NotNull(message = "Amount is required")
        BigDecimal amount,
        @NotNull(message = "User ID is required")
        UUID userId, //Todo: remove user Id from request body when implements the JWT token, and get userId from token instead
        @NotNull(message = "Category ID is required")
        Long categoryId
){}
