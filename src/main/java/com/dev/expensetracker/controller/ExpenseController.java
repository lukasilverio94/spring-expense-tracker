package com.dev.expensetracker.controller;

import com.dev.expensetracker.domain.dto.ExpenseFilterDTO;
import com.dev.expensetracker.domain.dto.ExpenseRequestDTO;
import com.dev.expensetracker.domain.dto.ExpenseResponseDTO;
import com.dev.expensetracker.domain.dto.ExpenseSummaryResponseDTO;
import com.dev.expensetracker.domain.projection.ExpenseView;
import com.dev.expensetracker.service.ExpenseService;
import com.dev.expensetracker.util.UriBuilderHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UriBuilderHelper uriBuilder;

    public ExpenseController(ExpenseService expenseService, UriBuilderHelper uriBuilder) {
        this.expenseService = expenseService;
        this.uriBuilder = uriBuilder;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> create(@Valid @RequestBody ExpenseRequestDTO dto) {
        ExpenseResponseDTO saved = expenseService.create(dto);
        URI locationHeader = uriBuilder.buildLocationUri(saved.expenseId());
        return ResponseEntity.created(locationHeader).body(saved);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseView> findById(@PathVariable Long expenseId) {
        return ResponseEntity.ok().body(expenseService.findById(expenseId));
    }

    @GetMapping
    public Page<ExpenseView> getExpenses(
            @RequestParam UUID userId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return expenseService.findByUserAndOptionalCategory(userId, categoryId, pageable);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDTO> update(@PathVariable Long expenseId, @Valid @RequestBody ExpenseRequestDTO dto) {
        return ResponseEntity.ok(expenseService.update(expenseId, dto));
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> delete(@PathVariable Long expenseId) {
        expenseService.deleteById(expenseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummaryByDateRange(@RequestParam UUID userId) {
        ExpenseSummaryResponseDTO summary = expenseService.getTotalExpensesByDateRange(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Expense summary fetched successfully");
        response.put("summary", summary);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/total")
    public ResponseEntity<Object> getTotalAmountByUserId(@RequestParam UUID userId) {
        BigDecimal total = expenseService.getTotalAmountByUserId(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Total expenses (includes all categories and dates) fetched successfully");
        response.put("total", total);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ExpenseResponseDTO>> filterExpenses(
            ExpenseFilterDTO filterDTO,
            @PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ExpenseResponseDTO> filteredExpenses = expenseService.filterExpenses(filterDTO, pageable);
        return ResponseEntity.ok(filteredExpenses);
    }

}
