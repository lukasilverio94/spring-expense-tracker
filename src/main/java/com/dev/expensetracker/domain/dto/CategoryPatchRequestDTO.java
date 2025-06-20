package com.dev.expensetracker.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryPatchRequestDTO (
        @NotBlank
        String name
){
}
