package com.NewsLetterService.NewsLetterService.customExceptions.subscriberExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class DuplicateSubscriberException extends BaseAppException {
    public DuplicateSubscriberException(String email) {
        super("Subscriber with email '" + email + "' already exists.");
    }
}
