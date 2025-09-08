package com.eterna.backend.core.shared.application.utils;

import java.util.Map;

public class HtmlTemplateUtils {

    public static String replacePlaceholders(String html, Map<String, String> values) {
        if (html == null || values == null || values.isEmpty()) return html;

        String result = html;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            result = result.replace(placeholder, entry.getValue());
        }

        return result;
    }
}
