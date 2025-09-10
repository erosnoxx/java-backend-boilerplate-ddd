package com.eterna.backend.api.rest.controllers;

import com.eterna.backend.api.rest.schemas.request.auth.*;
import com.eterna.backend.api.rest.schemas.response.auth.LoginResponse;
import com.eterna.backend.api.rest.schemas.response.auth.RefreshTokenResponse;
import com.eterna.backend.api.rest.schemas.response.common.IdResponse;
import com.eterna.backend.core.auth.application.contracts.usecases.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController @RequestMapping("auth")
public class AuthController {
    @Autowired
    private LoginUseCase loginUseCase;
    @Autowired
    private RefreshAccessTokenUseCase refreshAccessTokenUseCase;
    @Autowired
    private ActivateUserUseCase activateUserUseCase;
    @Autowired
    private RegisterUserUseCase registerUserUseCase;
    @Autowired
    private ChangePasswordUseCase changePasswordUseCase;

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

    @PostMapping("register")
    public ResponseEntity<IdResponse<UUID>> registerUser(@RequestBody @Valid RegisterUserRequest request) {
        var response = registerUserUseCase.execute(request.toInput());
        var location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(response)
                .toUri();

        return ResponseEntity.created(location).body(new IdResponse<>(response));
    }

    @PatchMapping("activate/{id}")
    public void activateUser(@PathVariable UUID id, @RequestBody ActivateUserRequest request) {
        activateUserUseCase.execute(request.toInput(id));
    }

    @PatchMapping("change-password/{id}")
    public void changePassword(@PathVariable UUID id, @RequestBody ChangePasswordRequest request) {
        changePasswordUseCase.execute(request.toInput(id));
    }
}
