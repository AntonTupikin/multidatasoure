package com.example.multidatasoure.controller.response;

import com.example.multidatasoure.entity.primary.Role;

public record UserResponse(Long id,
                           String username,
                           String email,
                           Role role) {

}

