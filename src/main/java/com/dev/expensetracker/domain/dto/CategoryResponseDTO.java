package com.dev.expensetracker.domain.dto;

import java.time.LocalDateTime;

public record CategoryResponseDTO(
        Long categoryId,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
