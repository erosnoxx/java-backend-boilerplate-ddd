package com.eterna.backend.infrastructure.seeders.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.InputStream;

@Slf4j
public abstract class Seeder<E, ID, R extends JpaRepository<E, ID>> implements CommandLineRunner {

    protected final R repository;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private JsonNode rootNode;

    protected Seeder(R repository, ResourceLoader resourceLoader) {
        this.repository = repository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            seed();
        }
    }

    protected abstract void seed();

    protected void loadSeederFile(String seederFile) {
        try {
            Resource resource = resourceLoader.getResource(seederFile);
            try (InputStream is = resource.getInputStream()) {
                rootNode = objectMapper.readTree(is);
            }
        } catch (Exception e) {
            log.error("❌ Failed to load seeder file {}", seederFile, e);
            rootNode = objectMapper.createObjectNode();
        }
    }

    protected  <T> T getAsObject(String path, Class<T> clazz) {
        if (rootNode == null) return null;

        String[] parts = path.split("\\.");
        JsonNode current = rootNode;
        for (String part : parts) {
            if (current == null) return null;
            current = current.get(part);
        }

        if (current == null || current.isNull()) return null;

        try {
            return objectMapper.treeToValue(current, clazz);
        } catch (Exception e) {
            log.error("❌ Failed to convert node {} to {}", path, clazz.getSimpleName(), e);
            return null;
        }
    }
}
