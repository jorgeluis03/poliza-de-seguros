package com.example.email.service;

import com.example.policy.model.Poliza;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);

    void sendMessageUsingThymeleafTemplate(String to,
                                           String subject,
                                           Poliza templateModel)
            throws IOException, MessagingException;
}
