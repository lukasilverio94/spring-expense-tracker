package com.dev.expensetracker.domain.mapper;

import com.dev.expensetracker.domain.dto.CategoryRequestDTO;
import com.dev.expensetracker.domain.dto.CategoryResponseDTO;
import com.dev.expensetracker.domain.entity.AppUser;
import com.dev.expensetracker.domain.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDTO dto, AppUser user) {
        Category category = new Category();
        category.setName(dto.name());
        category.setUser(user);
        return category;
    }

    public CategoryResponseDTO toResponse(Category category) {
        return new CategoryResponseDTO(category.getCategoryId(), category.getName(), category.getCreatedAt(), category.getUpdatedAt());
    }
}
