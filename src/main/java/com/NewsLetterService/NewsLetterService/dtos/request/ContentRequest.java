package com.NewsLetterService.NewsLetterService.dtos.request;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ContentRequest {
    private Long topicId;
    private String title;
    private String body;
    private LocalDateTime scheduledAt;
}
