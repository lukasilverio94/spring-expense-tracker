package com.dev.expensetracker.repository;

import com.dev.expensetracker.domain.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
}
