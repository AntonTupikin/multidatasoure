package com.example.multidatasoure.scenario.user;

import com.example.multidatasoure.controller.request.UserCreateRequest;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.entity.primary.Role;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.UserMapper;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCreateScenario {
    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse create(UserCreateRequest request) {
        log.info("Create user %s", request.toString());
        User user = userService.save(request, Role.SUPERVISOR, null);
        //PasswordResetToken passwordResetToken = authenticationService.createPasswordResetTokenForUser(user);
        //registrationRequestService.updateRegistrationRequest(user);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse createEmployee(UserCreateRequest request, Long userId) {
        log.info("Create employee for user %d".formatted(userId));
        User user = userService.findById(userId);
        return userMapper.toUserResponse(userService.save(request, Role.EMPLOYEE, user));
        //PasswordResetToken passwordResetToken = authenticationService.createPasswordResetTokenForUser(user);
    }
}
