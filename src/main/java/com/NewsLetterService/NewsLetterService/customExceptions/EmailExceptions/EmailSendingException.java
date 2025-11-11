package com.NewsLetterService.NewsLetterService.customExceptions.EmailExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class EmailSendingException extends BaseAppException {
    public EmailSendingException(String message) {
        super("Failed to send email: " + message);
    }
}
