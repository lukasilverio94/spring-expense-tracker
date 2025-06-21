package com.dev.expensetracker.service;

import com.dev.expensetracker.domain.dto.ExpenseRequestDTO;
import com.dev.expensetracker.domain.dto.ExpenseResponseDTO;
import com.dev.expensetracker.domain.entity.Category;
import com.dev.expensetracker.domain.entity.Expense;
import com.dev.expensetracker.domain.mapper.ExpenseMapper;
import com.dev.expensetracker.domain.projection.ExpenseView;
import com.dev.expensetracker.exception.NotFoundException;
import com.dev.expensetracker.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ExpenseResponseDTO create(ExpenseRequestDTO dto) {
        Expense expense = expenseMapper.toEntity(dto);

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
            Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
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
