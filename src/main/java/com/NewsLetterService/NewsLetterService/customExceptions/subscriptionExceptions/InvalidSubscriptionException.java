package com.NewsLetterService.NewsLetterService.customExceptions.subscriptionExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class InvalidSubscriptionException extends BaseAppException {
    public InvalidSubscriptionException(String message) {
        super(message);
    }
}
