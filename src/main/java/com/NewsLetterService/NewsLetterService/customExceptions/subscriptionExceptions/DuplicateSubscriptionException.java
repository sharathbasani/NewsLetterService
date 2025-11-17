package com.NewsLetterService.NewsLetterService.customExceptions.subscriptionExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class DuplicateSubscriptionException extends BaseAppException {
    public DuplicateSubscriptionException(Long subscriberId, Long topicId) {
        super("Subscriber " + subscriberId + " is already subscribed to topic " + topicId);
    }
}
