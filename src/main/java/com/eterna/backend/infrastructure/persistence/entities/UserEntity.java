package com.eterna.backend.infrastructure.persistence.entities;

import com.eterna.backend.core.auth.domain.enums.Role;
import com.eterna.backend.core.shared.domain.enums.EntityStatus;
import com.eterna.backend.infrastructure.persistence.entities.common.BaseEntity;
import com.eterna.backend.infrastructure.utils.RoleToAuthoritiesUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity @Table(name = "users")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserEntity extends BaseEntity<UUID> implements UserDetails {
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    @Column(nullable = false, length = 100, name = "password_hash")
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return RoleToAuthoritiesUtils.map(role);
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return status.isEnabled();
    }
}
