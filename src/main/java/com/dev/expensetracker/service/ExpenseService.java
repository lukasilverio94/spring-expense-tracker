package com.dev.expensetracker.service;

import com.dev.expensetracker.domain.dto.ExpenseFilterDTO;
import com.dev.expensetracker.domain.dto.ExpenseRequestDTO;
import com.dev.expensetracker.domain.dto.ExpenseResponseDTO;
import com.dev.expensetracker.domain.dto.ExpenseSummaryResponseDTO;
import com.dev.expensetracker.domain.entity.AppUser;
import com.dev.expensetracker.domain.entity.Category;
import com.dev.expensetracker.domain.entity.Expense;
import com.dev.expensetracker.domain.mapper.ExpenseMapper;
import com.dev.expensetracker.domain.projection.ExpenseView;
import com.dev.expensetracker.exception.NotFoundException;
import com.dev.expensetracker.repository.ExpenseRepository;
import com.dev.expensetracker.specs.ExpenseSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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

    public Page<ExpenseView> findByUserAndOptionalCategory(UUID userId, Long categoryId, Pageable pageable) {
        if (categoryId != null) {
            return expenseRepository.findAllByUserIdAndCategoryId(userId, categoryId, pageable);
        } else {
            return expenseRepository.findAllByUserUserId(userId, pageable);
        }

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

    public ExpenseSummaryResponseDTO getTotalExpensesByDateRange(UUID userId) {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime startOfWeek = today.with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime startOfYear = today.with(TemporalAdjusters.firstDayOfYear()).atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        BigDecimal monthly = expenseRepository.sumByUserAndDateRange(userId, startOfMonth, now);
        BigDecimal weekly = expenseRepository.sumByUserAndDateRange(userId, startOfWeek, now);
        BigDecimal yearly = expenseRepository.sumByUserAndDateRange(userId, startOfYear, now);

        return new ExpenseSummaryResponseDTO(
                monthly != null ? monthly : BigDecimal.ZERO,
                weekly != null ? weekly : BigDecimal.ZERO,
                yearly != null ? yearly : BigDecimal.ZERO
        );
    }

    public BigDecimal getTotalAmountByUserId(UUID userId) {
        return expenseRepository.getTotalAmountByUserId(userId);
    }

    public Page<ExpenseResponseDTO> filterExpenses(ExpenseFilterDTO filterDTO, Pageable pageable) {
        Specification<Expense> spec = ExpenseSpecifications.withFilters(filterDTO);
        return expenseRepository.findAll(spec, pageable)
                .map(expenseMapper::toResponse);
    }


}
