package com.eterna.backend.infrastructure.seeders.dto;

import lombok.Data;

@Data
public class AdminConfig {
    private String name;
    private String email;
    private String password;
}