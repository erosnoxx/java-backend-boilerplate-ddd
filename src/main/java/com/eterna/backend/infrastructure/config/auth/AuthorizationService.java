package com.eterna.backend.infrastructure.config.auth;

import com.eterna.backend.infrastructure.persistence.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private UserJpaRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("invalid email or password");
        }
        return user;
    }
}