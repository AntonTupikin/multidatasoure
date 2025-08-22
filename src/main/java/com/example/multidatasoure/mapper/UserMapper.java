package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.EmployeeResponse;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface UserMapper {
    UserResponse toUserResponse(User user);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "organizations", source = "orgs")
    EmployeeResponse toEmployeeResponse(User user, List<Organization> orgs);
}

