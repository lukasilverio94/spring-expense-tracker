package com.dev.expensetracker.repository;

import com.dev.expensetracker.domain.entity.Expense;
import com.dev.expensetracker.domain.projection.ExpenseView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("""
                SELECT 
                    e.expenseId AS expenseId,
                    e.description AS description,
                    e.dateTime AS dateTime,
                    e.amount AS amount,
                    c.categoryId AS categoryId,
                    c.name AS categoryName,
                    e.createdAt AS createdAt,
                    e.updatedAt AS updatedAt
                FROM Expense e
                JOIN e.category c
                WHERE e.expenseId = :id
            """)
    Optional<ExpenseView> findProjectedById(@Param("id") Long id);

    @Query("""
                SELECT 
                    e.expenseId AS expenseId,
                    e.description AS description,
                    e.dateTime AS dateTime,
                    e.amount AS amount,
                    c.categoryId AS categoryId,
                    c.name AS categoryName,
                    e.createdAt AS createdAt,
                    e.updatedAt AS updatedAt
                FROM Expense e
                JOIN e.category c
                WHERE e.user.userId = :userId
            """)
    Page<ExpenseView> findAllByUserUserId(UUID userId, Pageable pageable);

}
