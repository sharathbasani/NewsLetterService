package com.NewsLetterService.NewsLetterService.customExceptions.subscriberExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class SubscriberNotFoundException extends BaseAppException {
    public SubscriberNotFoundException(Long id) {
        super("Subscriber with ID " + id + " not found.");
    }
}
