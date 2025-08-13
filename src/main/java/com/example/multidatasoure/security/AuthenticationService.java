package com.example.multidatasoure.security;

import com.example.multidatasoure.controller.request.LoginRequest;
import com.example.multidatasoure.controller.response.LoginResponse;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.service.LogEntriesService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final LogEntriesService logEntriesService;

    public LoginResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        User user = userService.getByUsername(request.username());
        logEntriesService.save("Log into lk user %s %s".formatted(user.getUsername(), OffsetDateTime.now()));
        return generateTokens(auth);
    }

    private LoginResponse generateTokens(Authentication authentication) {
        String accessToken = jwtTokenProvider.createToken(authentication);
        return new LoginResponse(accessToken, "Bearer");
    }


}
