package com.dev.expensetracker.domain.mapper;

import com.dev.expensetracker.domain.dto.ExpenseRequestDTO;
import com.dev.expensetracker.domain.dto.ExpenseResponseDTO;
import com.dev.expensetracker.domain.entity.AppUser;
import com.dev.expensetracker.domain.entity.Category;
import com.dev.expensetracker.domain.entity.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public Expense toEntity(ExpenseRequestDTO dto, AppUser user, Category category) {
        Expense expense = new Expense();
        expense.setDescription(dto.description());
        expense.setDateTime(dto.dateTime());
        expense.setAmount(dto.amount());
        expense.setUser(user);
        expense.setCategory(category);
        return expense;
    }

    public ExpenseResponseDTO toResponse(Expense expense) {
        return new ExpenseResponseDTO(
                expense.getExpenseId(),
                expense.getDescription(),
                expense.getDateTime(),
                expense.getAmount(),
                expense.getCategory().getCategoryId(),
                expense.getCategory().getName(),
                expense.getCreatedAt(),
                expense.getUpdatedAt());
    }
}
