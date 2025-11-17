package com.NewsLetterService.NewsLetterService.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberResponse {
	private Long id;
	private String name;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdatedAt;
	private List<SubscriptionResponse> subscriptions;
}
