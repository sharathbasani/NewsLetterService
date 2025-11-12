package com.NewsLetterService.NewsLetterService.customExceptions.contentExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class ContentNotFoundException extends BaseAppException {
    public ContentNotFoundException(Long id) {
        super("Content with ID " + id + " not found.");
    }
}
