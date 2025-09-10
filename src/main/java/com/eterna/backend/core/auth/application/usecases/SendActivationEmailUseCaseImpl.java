package com.eterna.backend.core.auth.application.usecases;

import com.eterna.backend.core.auth.application.contracts.repositories.UserAuthRepository;
import com.eterna.backend.core.auth.application.contracts.usecases.SendActivationEmailUseCase;
import com.eterna.backend.core.auth.domain.objects.OtpCode;
import com.eterna.backend.core.shared.application.MailService;
import com.eterna.backend.core.shared.application.services.CacheService;
import com.eterna.backend.core.shared.application.utils.HtmlTemplateUtils;
import com.eterna.backend.core.shared.domain.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SendActivationEmailUseCaseImpl implements SendActivationEmailUseCase {
    @Autowired
    private UserAuthRepository repository;
    @Autowired
    private MailService mailService;
    @Autowired
    private CacheService cacheService;
    @Value("${html.activation.template}")
    private String htmlTemplatePath;
    @Value("${email.activation.subject}")
    private String activationEmailSubject;
    private static final int OTP_LENGTH = 6;

    @Override
    public Void execute(UUID command) {
        var user = repository.findById(command)
                .orElseThrow(() -> new NotFoundException("user not found"));

        var otpCode = OtpCode.of(generateOtp());

        var message = buildMessage(user.getName().getValue(), otpCode.getValue());

        mailService.sendEmail(
                user.getEmail().getValue(),
                activationEmailSubject,
                message);

        cacheService.put(
                otpCode.getKey(user.getId()),
                otpCode.getValue(),
                Duration.ofMinutes(10));

        return null;
    }

    private String buildMessage(String userName, String otpCode) {
        Map<String, String> values = new HashMap<>();
        values.put("NAME", userName);
        values.put("OTP_CODE", otpCode);

        return HtmlTemplateUtils.replacePlaceholders(htmlTemplatePath, values);
    }

    private String generateOtp() {
        return String.format("%0" + OTP_LENGTH + "d",
                ThreadLocalRandom.current().nextInt(1_000_000));
    }
}
