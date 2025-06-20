package com.dev.expensetracker.service;

import com.dev.expensetracker.domain.dto.CategoryPatchRequestDTO;
import com.dev.expensetracker.domain.dto.CategoryRequestDTO;
import com.dev.expensetracker.domain.dto.CategoryResponseDTO;
import com.dev.expensetracker.domain.entity.Category;
import com.dev.expensetracker.domain.mapper.CategoryMapper;
import com.dev.expensetracker.exception.NotFoundException;
import com.dev.expensetracker.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO requestDto) {
        Category category = categoryMapper.toEntity(requestDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }

    public CategoryResponseDTO findById(Long id) {
        return categoryMapper.toResponse(findEntityByIdOrThrow(id));
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Transactional
    public CategoryResponseDTO updateName(Long id, CategoryPatchRequestDTO requestDto) {
        var category = findEntityByIdOrThrow(id);
        category.setName(requestDto.name());
        return categoryMapper.toResponse(categoryRepository.save(category));
    }


    @Transactional
    public void deleteById(Long categoryId) {
        var category = findEntityByIdOrThrow(categoryId);
        categoryRepository.delete(category);
    }

    // helper method to find id or throw exception
    public Category findEntityByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found") {
                });
    }

}
