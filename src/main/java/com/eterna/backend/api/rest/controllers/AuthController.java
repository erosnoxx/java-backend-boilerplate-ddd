package com.eterna.backend.api.rest.controllers;

import com.eterna.backend.api.rest.schemas.request.auth.LoginRequest;
import com.eterna.backend.api.rest.schemas.request.auth.RefreshTokenRequest;
import com.eterna.backend.api.rest.schemas.response.auth.LoginResponse;
import com.eterna.backend.api.rest.schemas.response.auth.RefreshTokenResponse;
import com.eterna.backend.api.rest.schemas.response.common.IdResponse;
import com.eterna.backend.core.auth.application.contracts.usecases.LoginUseCase;
import com.eterna.backend.core.auth.application.contracts.usecases.RefreshAccessTokenUseCase;
import com.eterna.backend.core.auth.application.contracts.usecases.TestUseCase;
import com.eterna.backend.infrastructure.annotations.EmployerOnly;
import com.eterna.backend.infrastructure.persistence.entities.UserEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController @RequestMapping("auth")
public class AuthController {
    @Autowired
    private LoginUseCase loginUseCase;
    @Autowired
    private RefreshAccessTokenUseCase refreshAccessTokenUseCase;
    @Autowired
    private TestUseCase testUseCase;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(LoginResponse.of(
                loginUseCase.execute(request.toInput())
        ));
    }

    @PostMapping("refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            @RequestBody @Valid RefreshTokenRequest request) {
        return ResponseEntity.ok(RefreshTokenResponse.of(
                refreshAccessTokenUseCase.execute(request.toInput())
        ));
    }

    @GetMapping("/test")
    public void test() {
        testUseCase.execute(null);
    }
}
