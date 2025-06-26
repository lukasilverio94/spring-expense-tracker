package com.dev.expensetracker.domain.mapper;

import com.dev.expensetracker.domain.dto.UserRequestDTO;
import com.dev.expensetracker.domain.dto.UserResponseDTO;
import com.dev.expensetracker.domain.entity.AppUser;

public class UserMapper {

    public static AppUser toEntity(UserRequestDTO dto) {
        AppUser user = new AppUser();
        user.setEmail(dto.email());
        return user;
    }

    public static UserResponseDTO toResponse(AppUser user) {
        return new UserResponseDTO(user.getUserId(), user.getEmail());
    }

}
