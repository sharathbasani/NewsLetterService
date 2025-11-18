package com.NewsLetterService.NewsLetterService.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrevoEmailRequest {
    private String subject;
    private String htmlContent;
    private List<Map<String, String>> to;
    private Map<String, String> sender;

    public void setSender(String sender) {
        this.sender = Map.of("email", sender);
    }

    public void addToEmail(String toEmail) {
        if(this.to == null) {
            this.to = new ArrayList<>();
        }
        this.to.add(Map.of("email", toEmail));
    }
}
