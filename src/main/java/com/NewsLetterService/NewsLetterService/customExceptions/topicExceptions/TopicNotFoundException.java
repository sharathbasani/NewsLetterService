package com.NewsLetterService.NewsLetterService.customExceptions.topicExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class TopicNotFoundException extends BaseAppException {
    public TopicNotFoundException(Long id) {
        super("Topic with ID " + id + " not found.");
    }
}
