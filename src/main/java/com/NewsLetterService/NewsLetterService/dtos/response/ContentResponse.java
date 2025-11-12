package com.NewsLetterService.NewsLetterService.dtos.response;

import lombok.*;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentResponse {
    private Long id;
    private String title;
    private String body;
    private LocalDateTime scheduledAt;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}
