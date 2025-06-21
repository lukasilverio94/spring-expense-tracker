package com.dev.expensetracker.domain.mapper;

import com.dev.expensetracker.domain.dto.ExpenseRequestDTO;
import com.dev.expensetracker.domain.dto.ExpenseResponseDTO;
import com.dev.expensetracker.domain.entity.AppUser;
import com.dev.expensetracker.domain.entity.Category;
import com.dev.expensetracker.domain.entity.Expense;
import com.dev.expensetracker.repository.CategoryRepository;
import com.dev.expensetracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseMapper(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Expense toEntity(ExpenseRequestDTO dto) {
        AppUser user = userRepository.findById(dto.userId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Category category = categoryRepository.findById(dto.categoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found"));

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
