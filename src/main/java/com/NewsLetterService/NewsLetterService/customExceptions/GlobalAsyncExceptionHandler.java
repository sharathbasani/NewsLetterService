package com.NewsLetterService.NewsLetterService.customExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.EmailExceptions.EmailProviderUnavailableException;
import com.NewsLetterService.NewsLetterService.customExceptions.EmailExceptions.EmailSendingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class GlobalAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        if (ex instanceof EmailProviderUnavailableException) {
            log.error("Email provider unavailable. Method: {}, Params: {}", method.getName(), params);
            return;
        }

        if (ex instanceof EmailSendingException) {
            log.error("Failed to send email. Method: {}, Params: {}, Reason: {}", method.getName(), params, ex.getMessage());
            return;
        }

        log.error("Async exception in {} with params {}: {}", method.getName(), params, ex.getMessage(), ex);
    }
}
