package com.dev.expensetracker.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExpenseResponseDTO(
        Long expenseId,
        String description,
        LocalDateTime dateTime,
        BigDecimal amount,
        UUID userId,
        String userEmail,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
