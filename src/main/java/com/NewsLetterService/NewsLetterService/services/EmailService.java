package com.NewsLetterService.NewsLetterService.services;

import com.NewsLetterService.NewsLetterService.dtos.request.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.fromSender}")
    private String fromSender;

    public void sendSimpleEmail(EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailRequest.getToEmail());
        message.setSubject(emailRequest.getSubject());
        message.setText(emailRequest.getBody());
        message.setFrom(fromSender);
        mailSender.send(message);
    }
}
