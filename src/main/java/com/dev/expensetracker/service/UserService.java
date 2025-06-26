package com.dev.expensetracker.service;

import com.dev.expensetracker.domain.dto.UserRequestDTO;
import com.dev.expensetracker.domain.dto.UserResponseDTO;
import com.dev.expensetracker.domain.entity.AppUser;
import com.dev.expensetracker.domain.mapper.UserMapper;
import com.dev.expensetracker.exception.EmailAlreadyExistsException;
import com.dev.expensetracker.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/api/users")
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
}
