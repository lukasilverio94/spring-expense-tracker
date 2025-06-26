package com.dev.expensetracker.repository;

import com.dev.expensetracker.domain.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
