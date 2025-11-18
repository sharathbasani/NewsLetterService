package com.NewsLetterService.NewsLetterService.services;

import com.NewsLetterService.NewsLetterService.customExceptions.EmailExceptions.EmailProviderUnavailableException;
import com.NewsLetterService.NewsLetterService.customExceptions.EmailExceptions.EmailSendingException;
import com.NewsLetterService.NewsLetterService.dtos.request.BrevoEmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.url}")
    private String apiUrl;

    public void sendEmail(String toEmail, String subject, String body) {
        RestTemplate restTemplate = new RestTemplate();

        BrevoEmailRequest brevoEmailRequest = BrevoEmailRequest.builder()
                .subject(subject)
                .htmlContent(body)
                .build();
        brevoEmailRequest.setSender(senderEmail);
        brevoEmailRequest.addToEmail(toEmail);

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            HttpEntity<BrevoEmailRequest> entity = new HttpEntity<>(brevoEmailRequest, headers);
            restTemplate.postForEntity(apiUrl, entity, String.class);
        }
        catch (RestClientException exception) {
            throw new EmailProviderUnavailableException();
        }
        catch (Exception e) {
            throw new EmailSendingException(toEmail, e.getMessage());
        }
    }
}
