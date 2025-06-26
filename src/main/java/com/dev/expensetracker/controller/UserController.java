package com.dev.expensetracker.controller;

import com.dev.expensetracker.domain.dto.UserRequestDTO;
import com.dev.expensetracker.domain.dto.UserResponseDTO;
import com.dev.expensetracker.service.UserService;
import com.dev.expensetracker.util.UriBuilderHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final UriBuilderHelper uriBuilder;

    public UserController(UserService userService, UriBuilderHelper uriBuilder) {
        this.userService = userService;
        this.uriBuilder = uriBuilder;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO dto) {
        UserResponseDTO newUser = userService.create(dto);
        URI location = uriBuilder.buildLocationUri(newUser.userId());
        return ResponseEntity.created(location).body(newUser);
    }
}
