package com.NewsLetterService.NewsLetterService.customExceptions.contentExceptions;

import com.NewsLetterService.NewsLetterService.customExceptions.BaseAppException;

public class DuplicateContentException extends BaseAppException {
    public DuplicateContentException(String title) {
        super("Content with Title " + title + " already exists.");
    }
}
