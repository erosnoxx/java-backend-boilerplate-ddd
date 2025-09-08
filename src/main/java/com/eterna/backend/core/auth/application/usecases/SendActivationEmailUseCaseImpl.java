package com.eterna.backend.core.auth.application.usecases;

import com.eterna.backend.core.auth.application.contracts.repositories.UserAuthRepository;
import com.eterna.backend.core.auth.application.contracts.usecases.SendActivationEmailUseCase;
import com.eterna.backend.core.shared.application.MailService;
import com.eterna.backend.core.shared.application.utils.HtmlTemplateUtils;
import com.eterna.backend.core.shared.domain.exceptions.NotFoundException;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SendActivationEmailUseCaseImpl implements SendActivationEmailUseCase {
    @Autowired
    private UserAuthRepository repository;
    @Autowired
    private MailService mailService;
    @Value("${html.activation.template}")
    private String htmlTemplatePath;

    @Override
    public Void execute(UUID command) {
        var user = repository.findById(command)
                .orElseThrow(() -> new NotFoundException("user not found"));

        var message = buildMessage(user.getName(), user.getId().toString());

        mailService.sendEmail(
                user.getEmail().getValue(),
                "Eterna - Ative sua conta",
                message);
        return null;
    }

    private String buildMessage(String userName, String activationLink) {
        Map<String, String> values = new HashMap<>();
        values.put("NAME", userName);
        values.put("ACTIVATION_LINK", activationLink);

        var template = loadHtmlEmail();
        return HtmlTemplateUtils.replacePlaceholders(template, values);
    }

    private String loadHtmlEmail() {
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
