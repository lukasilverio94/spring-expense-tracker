package com.dev.expensetracker.repository;

import com.dev.expensetracker.domain.dto.CategoryResponseDTO;
import com.dev.expensetracker.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<CategoryResponseDTO> findAllByUserUserId(UUID userId);
}
