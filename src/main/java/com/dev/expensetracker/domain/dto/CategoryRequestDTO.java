package com.dev.expensetracker.domain.dto;

import java.util.UUID;

public record CategoryRequestDTO(
        String name,
        UUID userId
    ){
}
