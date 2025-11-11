package com.NewsLetterService.NewsLetterService.customExceptions.subscriptionExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class SubscriptionAlreadyExistsException extends BaseAppException {
    public SubscriptionAlreadyExistsException(Long subscriberId, Long topicId) {
        super("Subscriber " + subscriberId + " is already subscribed to topic " + topicId);
    }
}
