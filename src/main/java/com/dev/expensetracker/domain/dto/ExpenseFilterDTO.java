package com.dev.expensetracker.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExpenseFilterDTO(
        String description,
        LocalDateTime startDatetime,
        LocalDateTime endDatetime,
        BigDecimal minAmount,
        BigDecimal maxAmount,
        Long categoryId,
        String categoryName,
        UUID userId
) {
}
