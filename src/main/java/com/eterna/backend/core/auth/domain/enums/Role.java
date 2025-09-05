package com.eterna.backend.core.auth.domain.enums;

public enum Role {
    ADMIN("Administrator"),
    EMPLOYER("Employee"),
    CUSTOMER("Customer"),
    DEVELOPER("Developer");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean hasAdminPrivileges() {
        return this == ADMIN || this == DEVELOPER;
    }

    public boolean canAccessSystem() {
        return this != CUSTOMER;
    }
}

