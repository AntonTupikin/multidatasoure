package com.example.multidatasoure.scenario.user;

import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.service.OrganizationService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDeleteScenario {
    private final UserService userService;

    @Transactional
    public void delete(Long userId) {
        log.info("Delete user with id {}", userId);
        User user = userService.findById(userId);
        userService.delete(user);
    }
}
