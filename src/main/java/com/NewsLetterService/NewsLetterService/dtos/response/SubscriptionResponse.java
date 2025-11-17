package com.NewsLetterService.NewsLetterService.dtos.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionResponse {
	private Long id;
	private Long subscriberId;
	private Long topicId;
	private String subscriberName;
	private String topicName;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdatedAt;
}
