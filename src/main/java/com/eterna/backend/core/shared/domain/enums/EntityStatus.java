package com.eterna.backend.core.shared.domain.enums;

public enum EntityStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    SUSPENDED("Suspended");

    private final String displayName;

    EntityStatus(String displayName) {
        this.displayName = displayName;
    }

    public boolean isEnabled() {
        return this == ACTIVE;
    }

    public boolean canLogin() {
        return this == ACTIVE;
    }

    public String getDisplayName() {
        return displayName;
    }
}
