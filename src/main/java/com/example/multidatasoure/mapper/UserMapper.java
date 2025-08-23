package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.entity.primary.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}

