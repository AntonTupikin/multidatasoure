package com.example.multidatasoure.dto;

import com.example.multidatasoure.entity.primary.Role;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String email;
    private String password;
    private Role role;
}
