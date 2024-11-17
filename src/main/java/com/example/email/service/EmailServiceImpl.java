package com.example.email.service;
import com.example.policy.model.Poliza;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailServiceImpl implements EmailService{

    private static final String EMAIL_ADDRESS = "jorgel.dominguez03@gmail.com";

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    SpringTemplateEngine thymeleafTemplateEngine;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(EMAIL_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendMessageUsingThymeleafTemplate(String to, String subject, Poliza poliza) throws IOException, MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("numeroPoliza", poliza.getNumeroPoliza());
        templateModel.put("contratante", poliza.getUsuario().getCorreo());
        templateModel.put("inicioVigencia", poliza.getFechaInicio());
        templateModel.put("vencimientoVigencia", poliza.getFechaVencimiento());
        templateModel.put("tipoPoliza", poliza.getTipoPoliza());
        templateModel.put("estado", poliza.getEstado());

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);

        String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);

        sendHtmlMessage(to, subject, htmlBody);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        emailSender.send(message);
    }
}
