package com.NewsLetterService.NewsLetterService.controllers;

import com.NewsLetterService.NewsLetterService.dtos.request.EmailRequest;
import com.NewsLetterService.NewsLetterService.services.EmailService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody @NotNull EmailRequest emailRequest) {
        emailService.sendSimpleEmail(emailRequest);
        return ResponseEntity.ok("Email Sent Successfully");
    }
}
