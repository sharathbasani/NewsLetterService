package com.NewsLetterService.NewsLetterService.customExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.EmailExceptions.EmailProviderUnavailableException;
import com.NewsLetterService.NewsLetterService.customExceptions.EmailExceptions.EmailSendingException;
import com.NewsLetterService.NewsLetterService.customExceptions.contentExceptions.ContentNotFoundException;
import com.NewsLetterService.NewsLetterService.customExceptions.contentExceptions.DuplicateContentException;
import com.NewsLetterService.NewsLetterService.customExceptions.subscriberExceptions.DuplicateSubscriberException;
import com.NewsLetterService.NewsLetterService.customExceptions.subscriberExceptions.SubscriberNotFoundException;
import com.NewsLetterService.NewsLetterService.customExceptions.subscriptionExceptions.DuplicateSubscriptionException;
import com.NewsLetterService.NewsLetterService.customExceptions.subscriptionExceptions.SubscriptionNotFoundException;
import com.NewsLetterService.NewsLetterService.customExceptions.topicExceptions.DuplicateTopicException;
import com.NewsLetterService.NewsLetterService.customExceptions.topicExceptions.TopicNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
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
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Map<Class<? extends BaseAppException>, HttpStatus> EXCEPTION_STATUS_MAP = Map.ofEntries(
            Map.entry(SubscriberNotFoundException.class, HttpStatus.NOT_FOUND),
            Map.entry(TopicNotFoundException.class, HttpStatus.NOT_FOUND),
            Map.entry(SubscriptionNotFoundException.class, HttpStatus.NOT_FOUND),
            Map.entry(ContentNotFoundException.class, HttpStatus.NOT_FOUND),

            Map.entry(DuplicateSubscriberException.class, HttpStatus.CONFLICT),
            Map.entry(DuplicateSubscriptionException.class, HttpStatus.CONFLICT),
            Map.entry(DuplicateContentException.class, HttpStatus.CONFLICT),
            Map.entry(DuplicateTopicException.class, HttpStatus.CONFLICT),

            Map.entry(EmailSendingException.class, HttpStatus.SERVICE_UNAVAILABLE),
            Map.entry(EmailProviderUnavailableException.class, HttpStatus.SERVICE_UNAVAILABLE)
    );

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        log.warn("Validation failed: {} | Path: {}", messages,
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return buildErrorResponse(
                "ValidationError",
                messages,
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request) {

        List<String> messages = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();
        log.warn("Constraint violation: {} | Path: {}", messages,
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return buildErrorResponse(
                "ValidationError",
                messages,
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(BaseAppException.class)
    public ResponseEntity<Object> handleAppExceptions(BaseAppException ex, WebRequest request) {
        HttpStatus status = EXCEPTION_STATUS_MAP.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

        log.error("App exception: {} | Status: {} | Path: {}", ex.getMessage(), status,
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return buildErrorResponse(
                ex.getClass().getSimpleName(),
                List.of(ex.getMessage()),
                status,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex,
            @Nullable Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        log.error("Framework exception: {} | Status: {} | Path: {}", ex.getMessage(), status,
                ((ServletWebRequest) request).getRequest().getRequestURI());
        return buildErrorResponse(
                ex.getClass().getSimpleName(),
                List.of(ex.getMessage() != null ? ex.getMessage() : "Unexpected framework error"),
                HttpStatus.valueOf(status.value()),
                request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex) {

        log.error("Unhandled exception: ", ex);
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
