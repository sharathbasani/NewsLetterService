package com.NewsLetterService.NewsLetterService.dtos.response;

import lombok.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentResponse {
    private Long id;
    private String title;
    private String body;
    private LocalTime scheduledAt;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private String topicName;
}
