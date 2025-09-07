package com.eterna.backend.api.rest.controllers;

import com.eterna.backend.api.rest.schemas.response.common.IdResponse;
import com.eterna.backend.infrastructure.config.security.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController @RequestMapping("users")
public class UserController {
    @GetMapping("me")
    public ResponseEntity<IdResponse<UUID>> getCurrentUserId() {
        return ResponseEntity.ok(SecurityUtils.getCurrentUser());
    }
}
