package com.example.email.controller;

import com.example.email.service.EmailServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/api/polizas/email")
public class EmailController {
    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/send")
    public String sendEmail(@RequestParam String to) throws MessagingException {
        try {
            String subject = "Correo de Prueba";
            String body = "Cuerpo del correo de Prueba";
            emailService.sendSimpleMessage(to, subject, body);
            return "Correo enviado con éxito";
        } catch (Exception e) {
            return "Error al enviar el correo: " + e.getMessage();
        }
    }

    @GetMapping("/send-template")
    public String sendEmailWithTemplate(@RequestParam String to) {
        try {
            String subject = "Correo de Prueba con Plantilla";
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("variable1", "Valor para la variable 1");
            templateModel.put("variable2", "Valor para la variable 2");

            // Llama al metodo que usa la plantilla Thymeleaf
            emailService.sendMessageUsingThymeleafTemplate(to, subject, templateModel);

            return "Correo enviado con éxito con plantilla";
        } catch (Exception e) {
            return "Error al enviar el correo: " + e.getMessage();
        }
    }

}
