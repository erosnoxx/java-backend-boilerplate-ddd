package com.eterna.backend.api.rest.controllers;

import com.eterna.backend.api.rest.schemas.request.LoginRequest;
import com.eterna.backend.api.rest.schemas.response.LoginResponse;
import com.eterna.backend.core.auth.application.contracts.usecases.LoginUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("auth")
public class AuthController {
    @Autowired
    private LoginUseCase loginUseCase;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(LoginResponse.of(
                loginUseCase.execute(request.toInput())
        ));
    }
}
