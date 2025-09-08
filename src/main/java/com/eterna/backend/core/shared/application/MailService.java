package com.eterna.backend.core.shared.application;

public interface MailService {
    void sendEmail(
            String to,
            String subject,
            String text);
}
