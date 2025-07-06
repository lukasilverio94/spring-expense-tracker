package com.dev.expensetracker.domain.dto;

import java.math.BigDecimal;

public record ExpenseSummaryResponseDTO(BigDecimal monthly, BigDecimal weekly, BigDecimal yearly) {
}
