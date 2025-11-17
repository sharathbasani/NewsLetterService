package com.NewsLetterService.NewsLetterService.customExceptions.subscriptionExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class SubscriptionNotFoundException extends BaseAppException {
    public SubscriptionNotFoundException(Long subscriberId, Long topicId) {
        super("No subscription found for subscriber " + subscriberId + " and topic " + topicId);
    }

    public SubscriptionNotFoundException(Long id) {
        super("No subscription found for id " + id);
    }
}
