package com.NewsLetterService.NewsLetterService.customExceptions.EmailExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class EmailSendingException extends BaseAppException {
    public EmailSendingException(String toEmail, String message) {
        super(String.format("Failed to send email to %s, error : %s", toEmail, message));
    }
}
