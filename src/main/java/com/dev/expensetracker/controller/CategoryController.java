package com.dev.expensetracker.controller;

import com.dev.expensetracker.domain.dto.CategoryPatchRequestDTO;
import com.dev.expensetracker.domain.dto.CategoryRequestDTO;
import com.dev.expensetracker.domain.dto.CategoryResponseDTO;
import com.dev.expensetracker.service.CategoryService;
import com.dev.expensetracker.util.UriBuilderHelper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final UriBuilderHelper uriBuilder;

    public CategoryController(CategoryService categoryService, UriBuilderHelper uriBuilder) {
        this.categoryService = categoryService;
        this.uriBuilder = uriBuilder;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO saved = categoryService.create(dto);
        URI locationHeader = uriBuilder.buildLocationUri(saved.categoryId());
        return ResponseEntity.created(locationHeader).body(saved);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(categoryService.findById(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll() {
        List<CategoryResponseDTO> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long categoryId, @Valid @RequestBody CategoryPatchRequestDTO dto) {
        return ResponseEntity.ok(categoryService.updateName(categoryId, dto));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable Long categoryId) {
        categoryService.deleteById(categoryId);
        return ResponseEntity.noContent().build();
    }

}