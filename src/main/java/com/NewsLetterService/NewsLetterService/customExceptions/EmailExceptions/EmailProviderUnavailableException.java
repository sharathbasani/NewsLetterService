package com.NewsLetterService.NewsLetterService.customExceptions.EmailExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class EmailProviderUnavailableException extends BaseAppException {
    public EmailProviderUnavailableException() {
        super("Email provider is temporarily unavailable. Try again later.");
    }
}
