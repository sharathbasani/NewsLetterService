package com.NewsLetterService.NewsLetterService.customExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.EmailExceptions.EmailProviderUnavailableException;
import com.NewsLetterService.NewsLetterService.customExceptions.EmailExceptions.EmailSendingException;
import com.NewsLetterService.NewsLetterService.customExceptions.subscriberExceptions.DuplicateSubscriberException;
import com.NewsLetterService.NewsLetterService.customExceptions.subscriberExceptions.SubscriberNotFoundException;
import com.NewsLetterService.NewsLetterService.customExceptions.subscriptionExceptions.SubscriptionAlreadyExistsException;
import com.NewsLetterService.NewsLetterService.customExceptions.subscriptionExceptions.SubscriptionNotFoundException;
import com.NewsLetterService.NewsLetterService.customExceptions.topicExceptions.TopicNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        return buildErrorResponse(
                "ValidationError",
                messages,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request) {

        List<String> messages = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        return buildErrorResponse(
                "ValidationError",
                messages,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(BaseAppException.class)
    public ResponseEntity<Object> handleAppExceptions(BaseAppException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ex instanceof SubscriberNotFoundException ||
                ex instanceof TopicNotFoundException ||
                ex instanceof SubscriptionNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof DuplicateSubscriberException ||
                ex instanceof SubscriptionAlreadyExistsException) {
            status = HttpStatus.CONFLICT;
        } else if (ex instanceof EmailSendingException ||
                ex instanceof EmailProviderUnavailableException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        }

        return buildErrorResponse(
                ex.getClass().getSimpleName(),
                List.of(ex.getMessage()),
                status,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        return buildErrorResponse(
                ex.getClass().getSimpleName(),
                List.of(ex.getMessage() != null ? ex.getMessage() : "Unexpected framework error"),
                HttpStatus.valueOf(status.value()),
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", "InternalServerError");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildErrorResponse(
            String error,
            List<String> messages,
            HttpStatus status,
            WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("messages", messages);
        body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(body, status);
    }
}
