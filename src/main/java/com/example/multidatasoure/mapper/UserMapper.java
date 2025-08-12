package com.example.multidatasoure.mapper;

import org.mapstruct.Mapper;

import com.example.multidatasoure.dto.UserResponse;
import com.example.multidatasoure.entity.primary.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}

