package com.dev.expensetracker.domain.mapper;

import com.dev.expensetracker.domain.dto.CategoryRequestDTO;
import com.dev.expensetracker.domain.dto.CategoryResponseDTO;
import com.dev.expensetracker.domain.entity.AppUser;
import com.dev.expensetracker.domain.entity.Category;
import com.dev.expensetracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    private final UserRepository userRepository;

    public CategoryMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Category toEntity(CategoryRequestDTO dto) {
        AppUser user = userRepository.findById(dto.userId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Category category = new Category();
        category.setName(dto.name());
        category.setUser(user);
        return category;
    }

    public CategoryResponseDTO toResponse(Category category) {
        return new CategoryResponseDTO(category.getCategoryId(), category.getName(), category.getCreatedAt(), category.getUpdatedAt());
    }
}
