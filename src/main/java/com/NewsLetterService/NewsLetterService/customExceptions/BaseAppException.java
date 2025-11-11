package com.NewsLetterService.NewsLetterService.customExceptions;

public abstract class BaseAppException extends RuntimeException {
    public BaseAppException(String message) {
        super(message);
    }
}
