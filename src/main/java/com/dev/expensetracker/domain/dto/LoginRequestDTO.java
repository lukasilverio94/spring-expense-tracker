package com.dev.expensetracker.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(

        @NotBlank
        String email,
        @NotNull
        String password
) {
}
