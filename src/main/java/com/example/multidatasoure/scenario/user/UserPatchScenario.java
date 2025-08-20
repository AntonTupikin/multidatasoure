package com.example.multidatasoure.scenario.user;

import com.example.multidatasoure.controller.request.UserPatchRequest;
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
public class UserPatchScenario {
    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse patch(Long id, UserPatchRequest request) {
        User user = userService.findById(id);
        log.info("Patch user with id {}", id);
        if (request.password() != null) {
            userService.patchPassword(user, request.password());
        }
        log.info("Patch manager for user with id {} finished", id);
        return userMapper.toUserResponse(user);
    }
}
