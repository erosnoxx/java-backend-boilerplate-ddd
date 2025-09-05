package com.eterna.backend.infrastructure.utils;

import com.eterna.backend.core.auth.domain.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class RoleToAuthoritiesUtils {
    public static Collection<? extends GrantedAuthority> map(Role role) {
        return switch (role) {
            case CUSTOMER -> List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
            case EMPLOYER -> List.of(
                    new SimpleGrantedAuthority("ROLE_EMPLOYER"),
                    new SimpleGrantedAuthority("ROLE_CUSTOMER")
            );
            case ADMIN -> List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_EMPLOYER"),
                    new SimpleGrantedAuthority("ROLE_CUSTOMER")
            );
            case DEVELOPER -> List.of(
                    new SimpleGrantedAuthority("ROLE_DEVELOPER"),
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_EMPLOYER"),
                    new SimpleGrantedAuthority("ROLE_CUSTOMER")
            );
        };
    }
}
