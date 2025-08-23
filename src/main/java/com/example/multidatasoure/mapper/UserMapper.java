package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.EmployeeResponse;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.entity.primary.EmployeeProfile;
import com.example.multidatasoure.entity.primary.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface UserMapper {
    UserResponse toUserResponse(User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    EmployeeResponse toEmployeeResponse(User user);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "role", source = "user.role")
    EmployeeResponse toEmployeeResponse(EmployeeProfile employeeProfile);
}

