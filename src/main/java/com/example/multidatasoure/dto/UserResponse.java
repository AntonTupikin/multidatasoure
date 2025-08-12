package com.example.multidatasoure.dto;

import com.example.multidatasoure.entity.primary.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
}

