package com.dev.expensetracker.controller;

import com.dev.expensetracker.domain.dto.CategoryRequestDTO;
import com.dev.expensetracker.domain.dto.CategoryResponseDTO;
import com.dev.expensetracker.service.CategoryService;
import com.dev.expensetracker.util.UriBuilderHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO saved = categoryService.create(dto);
        URI locationHeader = uriBuilder.buildLocationUri(saved.categoryId());
        return ResponseEntity.created(locationHeader).body(saved);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(categoryService.findById(categoryId));

    }
}