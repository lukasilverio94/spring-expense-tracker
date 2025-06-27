package com.dev.expensetracker.service;

import com.dev.expensetracker.domain.dto.ExpenseRequestDTO;
import com.dev.expensetracker.domain.dto.ExpenseResponseDTO;
import com.dev.expensetracker.domain.entity.AppUser;
import com.dev.expensetracker.domain.entity.Category;
import com.dev.expensetracker.domain.entity.Expense;
import com.dev.expensetracker.domain.mapper.ExpenseMapper;
import com.dev.expensetracker.domain.projection.ExpenseView;
import com.dev.expensetracker.exception.NotFoundException;
import com.dev.expensetracker.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final CategoryService categoryService;
    private final UserService userService;

    public ExpenseService(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper, CategoryService categoryService, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Transactional
    public ExpenseResponseDTO create(ExpenseRequestDTO dto) {
        AppUser user = userService.findEntityByIdOrThrow(dto.userId());
        Category category = categoryService.findEntityByIdOrThrow(dto.categoryId());

        Expense expense = expenseMapper.toEntity(dto, user, category);

        if (expense.getDateTime() == null) {
            expense.setDateTime(LocalDateTime.now());
        }
        Expense savedExpense = expenseRepository.save(expense);
        return expenseMapper.toResponse(savedExpense);
    }

    public ExpenseView findById(Long expenseId) {
        return findProjectedByIdOrThrow(expenseId);
    }

    public Page<ExpenseView> findAllByUser(UUID userId, Pageable pageable) {
        return expenseRepository.findAllByUserUserId(userId, pageable);
    }

    @Transactional
    public ExpenseResponseDTO update(Long id, ExpenseRequestDTO dto) {
        var expense = findEntityByIdOrThrow(id);
        expense.setDescription(dto.description());
        expense.setAmount(dto.amount());
        expense.setDateTime(dto.dateTime());

        if (!expense.getCategory().getCategoryId().equals(dto.categoryId())) {
            Category category = categoryService.findEntityByIdOrThrow(dto.categoryId());
            expense.setCategory(category);
        }

        Expense updated = expenseRepository.save(expense);
        return expenseMapper.toResponse(updated);
    }

    @Transactional
    public void deleteById(Long expenseId) {
        var expense = findEntityByIdOrThrow(expenseId);
        expenseRepository.delete(expense);
    }

    // helper method to find id or throw exception
    public ExpenseView findProjectedByIdOrThrow(Long id) {
        return expenseRepository.findProjectedById(id)
                .orElseThrow(() -> new NotFoundException("Expense with id " + id + " not found") {
                });
    }

    public Expense findEntityByIdOrThrow(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense with id " + id + " not found"));
    }
}
