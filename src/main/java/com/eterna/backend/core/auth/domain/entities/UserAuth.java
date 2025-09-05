package com.eterna.backend.core.auth.domain.entities;

import com.eterna.backend.core.auth.domain.enums.Role;
import com.eterna.backend.core.auth.domain.objects.Password;
import com.eterna.backend.core.shared.domain.entities.Domain;
import com.eterna.backend.core.shared.domain.enums.EntityStatus;
import com.eterna.backend.core.shared.domain.objects.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
public class UserAuth extends Domain<UUID> {
    private Email email;
    private Password passwordHash;
    private Role role;
    private EntityStatus status;
    private LocalDateTime lastLogin;

    public UserAuth(UUID id) {
        setId(id);
    }

    public UserAuth(Email email, Password passwordHash, Role role, EntityStatus status) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
        this.lastLogin = LocalDateTime.now();
    }

    public boolean canAccess() {
        checkInactiveDueToLastLogin();
        return status.canLogin();
    }

    private void checkInactiveDueToLastLogin() {
        if (lastLogin == null) return;

        var cutoffDate = LocalDateTime.now().minusMonths(6);
        if (lastLogin.isBefore(cutoffDate) && status.isEnabled()) {
            suspend();
        }
    }

    public void registerLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    public boolean isPasswordValid(String plainPassword) {
        return passwordHash.matches(plainPassword);
    }

    public boolean hasRole(Role expectedRole) {
        return role == expectedRole;
    }

    public void activate() { status = EntityStatus.ACTIVE; }

    public void suspend() { status = EntityStatus.SUSPENDED; }

    public void deactivate() { status = EntityStatus.INACTIVE; }

    @Override
    public String toString() {
        return String.format("UserAuth{id=%s, email=%s, role=%s, status=%s}",
                getId(), email, role, status);
    }
}
