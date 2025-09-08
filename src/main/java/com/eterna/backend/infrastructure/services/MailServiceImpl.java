package com.eterna.backend.infrastructure.services;

import com.eterna.backend.core.shared.application.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(
            String to,
            String subject,
            String htmlContent) {
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);

            log.info("email send to {}", to);
        } catch (Exception e) {
            log.error("failed to send email: {}", e.getMessage());
            throw new RuntimeException("failed to send email", e);
        }
    }
}
