package com.example.multidatasoure.scenario.user;

import com.example.multidatasoure.controller.request.UserFilter;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.UserMapper;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserGetScenario {
    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponse get(Long userId) {
        User user = userService.findById(userId);
        return userMapper.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id, Long supervisorId) {
        User user = userService.findByIdAndSupervisorId(id,supervisorId);
        return userMapper.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllByUser(Long userId, Pageable pageable, UserFilter userFilter) {
        log.info("Get employees for user. {}. {}.", pageable, userFilter);
        Page<User> users = userService.getAllByUser(userService.findById(userId), pageable, userFilter);
        return users.map(userMapper::toUserResponse);
    }
}
