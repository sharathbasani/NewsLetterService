package com.NewsLetterService.NewsLetterService.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SubscriptionRequest {
    private Long subscriberId;
    private Long topicId;
}
