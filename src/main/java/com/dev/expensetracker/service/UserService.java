package com.dev.expensetracker.service;

import com.dev.expensetracker.domain.dto.UserRequestDTO;
import com.dev.expensetracker.domain.dto.UserResponseDTO;
import com.dev.expensetracker.domain.entity.AppUser;
import com.dev.expensetracker.domain.mapper.UserMapper;
import com.dev.expensetracker.exception.EmailAlreadyExistsException;
import com.dev.expensetracker.repository.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
public class UserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO create(@RequestBody UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException("Email is taken. Try another one");
        }

        AppUser user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        var savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }

    public AppUser findEntityByIdOrThrow(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID = " + userId));
    }
}
