package com.NewsLetterService.NewsLetterService.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    @Email
    private String toEmail;
    @NotBlank
    private String subject;
    @NotBlank
    private String body;
}
