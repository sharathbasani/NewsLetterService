package com.NewsLetterService.NewsLetterService.dtos.request;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ContentUpdateRequest {
    private String title;
    private String body;
    private LocalDateTime scheduledAt;
    private Long topicId;
}
