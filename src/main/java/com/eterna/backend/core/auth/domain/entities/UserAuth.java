package com.eterna.backend.core.auth.domain.entities;

import com.eterna.backend.core.auth.domain.enums.Role;
import com.eterna.backend.core.auth.domain.events.UserSignedUpEvent;
import com.eterna.backend.core.auth.domain.objects.Password;
import com.eterna.backend.core.auth.domain.objects.UserName;
import com.eterna.backend.core.auth.domain.checkers.UserUniquenessChecker;
import com.eterna.backend.core.shared.domain.entities.Domain;
import com.eterna.backend.core.shared.domain.enums.EntityStatus;
import com.eterna.backend.core.shared.domain.events.DomainEvent;
import com.eterna.backend.core.shared.domain.exceptions.AlreadyExistsException;
import com.eterna.backend.core.shared.domain.objects.Email;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter @Setter
public class UserAuth extends Domain<UUID> {
    private UserName name;
    private Email email;
    private Password passwordHash;
    private Role role;
    private EntityStatus status;
    private LocalDateTime lastLogin;

    private List<DomainEvent> events = new ArrayList<>();

    public UserAuth(UUID id) {
        setId(id);
    }

    public UserAuth(UserName name, Email email, Password passwordHash, Role role) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = EntityStatus.INACTIVE;
        this.lastLogin = LocalDateTime.now();
    }

    public static UserAuth signUpNew(
            UserName name, Email email, Role role, UserUniquenessChecker checker) {
        if (role == Role.ADMIN && checker.adminExists())
            throw new AlreadyExistsException("admin user already registered");

        if (!checker.isUnique(email.getValue()))
            throw new AlreadyExistsException("user already registered");

        return new UserAuth(name, email, Password.generateRandomPassword(), role);
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

    public void signUp() {
        events.add(
                new UserSignedUpEvent(
                        this.getId()));

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

    public List<DomainEvent> pullEvents() {
        var pulled = new ArrayList<>(events);
        events.clear();
        return pulled;
    }

    @Override
    public String toString() {
        return String.format("UserAuth{id=%s, email=%s, role=%s, status=%s}",
                getId(), email, role, status);
    }
}
