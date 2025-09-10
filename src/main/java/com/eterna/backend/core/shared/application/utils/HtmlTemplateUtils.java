package com.eterna.backend.core.shared.application.utils;

import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HtmlTemplateUtils {

    public static String replacePlaceholders(String htmlTemplatePath, Map<String, String> values) {
        var html = loadHtmlEmail(htmlTemplatePath);

        if (values == null || values.isEmpty()) return html;

        String result = html;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            result = result.replace(placeholder, entry.getValue());
        }

        return result;
    }

    private static String loadHtmlEmail(String htmlTemplatePath) {
        try {
            var resource = new ClassPathResource(htmlTemplatePath.replace("classpath:", ""));
            try (var is = resource.getInputStream()) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load HTML template", e);
        }
    }
}
