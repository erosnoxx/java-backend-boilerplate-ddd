package com.eterna.backend.api.rest.controllers;

import com.eterna.backend.api.rest.schemas.response.user.UserResponse;
import com.eterna.backend.infrastructure.persistence.entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("users")
public class UserController {
    @GetMapping("me")
    public ResponseEntity<UserResponse> getCurrentUserId(
            @AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(UserResponse.of(user));
    }
}
