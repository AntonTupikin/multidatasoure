package com.example.multidatasoure.controller;

import com.example.multidatasoure.dto.UserResponse;
import com.example.multidatasoure.mapper.UserMapper;
import com.example.multidatasoure.repository.primary.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AdminController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    public List<UserResponse> allUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }
}
