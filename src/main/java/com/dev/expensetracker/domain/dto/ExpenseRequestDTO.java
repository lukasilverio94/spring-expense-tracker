package com.dev.expensetracker.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExpenseRequestDTO(
        String description,
        LocalDateTime dateTime,
        BigDecimal amount,
        UUID userId,
        Long categoryId
){}
