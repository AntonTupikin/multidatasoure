package com.example.multidatasoure.scenario.user;

import com.example.multidatasoure.controller.response.UserResponse;
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
public class UserGetScenario {
    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse get(Long userId) {
        User user = userService.findById(userId);
        //PasswordResetToken passwordResetToken = authenticationService.createPasswordResetTokenForUser(user);
        //registrationRequestService.updateRegistrationRequest(user);
        return userMapper.toUserResponse(user);
    }
}
