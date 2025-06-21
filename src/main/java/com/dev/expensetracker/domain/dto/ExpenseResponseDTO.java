package com.dev.expensetracker.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseResponseDTO(
        Long expenseId,
        String description,
        LocalDateTime dateTime,
        BigDecimal amount,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
