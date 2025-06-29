package com.dev.expensetracker.controller;

import com.dev.expensetracker.domain.dto.ExpenseRequestDTO;
import com.dev.expensetracker.domain.dto.ExpenseResponseDTO;
import com.dev.expensetracker.domain.projection.ExpenseView;
import com.dev.expensetracker.service.ExpenseService;
import com.dev.expensetracker.util.UriBuilderHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public Page<ExpenseView> getExpensesByUser(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return expenseService.findAllByUser(userId, pageable);
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
}
