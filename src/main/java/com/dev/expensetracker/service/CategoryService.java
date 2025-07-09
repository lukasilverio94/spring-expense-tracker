package com.dev.expensetracker.service;

import com.dev.expensetracker.domain.dto.CategoryPatchRequestDTO;
import com.dev.expensetracker.domain.dto.CategoryRequestDTO;
import com.dev.expensetracker.domain.dto.CategoryResponseDTO;
import com.dev.expensetracker.domain.entity.AppUser;
import com.dev.expensetracker.domain.entity.Category;
import com.dev.expensetracker.domain.mapper.CategoryMapper;
import com.dev.expensetracker.exception.NotFoundException;
import com.dev.expensetracker.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserService userService;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.userService = userService;
    }

    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO requestDto) {
        AppUser user = userService.findEntityByIdOrThrow(requestDto.userId());
        Category category = categoryMapper.toEntity(requestDto, user);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }

    public CategoryResponseDTO findById(Long id) {
        return categoryMapper.toResponse(findEntityByIdOrThrow(id));
    }


    public List<CategoryResponseDTO> findAllByUserId(UUID userId) {
        return categoryRepository.findAllByUserUserId(userId);
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
