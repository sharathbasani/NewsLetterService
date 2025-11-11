package com.NewsLetterService.NewsLetterService.customExceptions.subscriberExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class InvalidEmailFormatException extends BaseAppException {
    public InvalidEmailFormatException(String email) {
        super("Email format is invalid: " + email);
    }
}
