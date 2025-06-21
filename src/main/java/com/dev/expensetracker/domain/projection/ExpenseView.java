package com.dev.expensetracker.domain.projection;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonPropertyOrder({"expenseId", "description", "amount", "dateTime", "categoryName", "categoryId", "createdAt", "updatedAt"})
public interface ExpenseView {

    Long getExpenseId();

    String getDescription();

    LocalDateTime getDateTime();

    BigDecimal getAmount();

    Long getCategoryId();

    String getCategoryName();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
