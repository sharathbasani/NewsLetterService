package com.NewsLetterService.NewsLetterService.customExceptions.topicExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class DuplicateTopicException extends BaseAppException {
    public DuplicateTopicException(String name) {
        super("Topic with name '" + name + "' already exists.");
    }
}
