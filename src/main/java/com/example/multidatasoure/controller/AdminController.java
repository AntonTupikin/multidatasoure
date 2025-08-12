package com.example.multidatasoure.controller;

import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.repository.primary.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        return userRepository.findAll();
    }
}
