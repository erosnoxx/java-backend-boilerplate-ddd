package com.eterna.backend.infrastructure.seeders;

import com.eterna.backend.core.auth.domain.enums.Role;
import com.eterna.backend.core.auth.domain.objects.Password;
import com.eterna.backend.core.shared.domain.enums.EntityStatus;
import com.eterna.backend.infrastructure.persistence.entities.UserEntity;
import com.eterna.backend.infrastructure.persistence.repositories.UserJpaRepository;
import com.eterna.backend.infrastructure.seeders.common.Seeder;
import com.eterna.backend.infrastructure.seeders.dto.AdminConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component @Slf4j
public class UserSeeder extends Seeder<UserEntity, UUID, UserJpaRepository> {

    @Value("${app.seeder.file-path}")
    private String seederFile;

    protected UserSeeder(
            UserJpaRepository repository,
            ResourceLoader resourceLoader) {
        super(repository, resourceLoader);
    }

    @Override
    protected void seed() {
        loadSeederFile(seederFile);

        AdminConfig admin = getAsObject("admin", AdminConfig.class);

        createAdmin(admin);
    }

    private void createAdmin(AdminConfig adminConfig) {
        var admin = new UserEntity();
        admin.setName(adminConfig.getName());
        admin.setEmail(adminConfig.getEmail());
        admin.setPasswordHash(new BCryptPasswordEncoder().encode(adminConfig.getPassword()));
        admin.setRole(Role.ADMIN);
        admin.setStatus(EntityStatus.ACTIVE);
        admin.setFirstPasswordChanged(true);

        repository.save(admin);

        log.info("⚡ Admin user created: {} / {}", adminConfig.getName(), adminConfig.getEmail());
    }
}
