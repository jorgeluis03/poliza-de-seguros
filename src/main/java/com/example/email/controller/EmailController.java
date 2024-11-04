package com.example.email.controller;

import com.example.email.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/polizas/email")
public class EmailController {
    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/send")
    public String sendEmail(@RequestParam String to) {
        try {
            String subject = "Correo de Prueba";
            String body = "Cuerpo del correo de Prueba";
            emailService.sendSimpleMessage(to, subject, body);
            return "Correo enviado con éxito";
        } catch (Exception e) {
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}
