package com.dev.expensetracker.repository;

import com.dev.expensetracker.domain.entity.Expense;
import com.dev.expensetracker.domain.projection.ExpenseView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {

    @Query("""
                 SELECT\s
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
            \s""")
    Optional<ExpenseView> findProjectedById(@Param("id") Long id);

    @Query("""
                 SELECT\s
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
            \s""")
    Page<ExpenseView> findAllByUserUserId(UUID userId, Pageable pageable);

    @Query("""
                 SELECT\s
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
                 AND c.categoryId = :categoryId
            \s""")
    Page<ExpenseView> findAllByUserIdAndCategoryId(
            @Param("userId") UUID userId,
            @Param("categoryId") Long categoryId,
            Pageable pageable);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.userId = :userId")
    BigDecimal getTotalAmountByUserId(@Param("userId") UUID userId);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.userId = :userId AND e.dateTime BETWEEN :start AND :end")
    BigDecimal sumByUserAndDateRange(
            @Param("userId") UUID userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime today);

   }
