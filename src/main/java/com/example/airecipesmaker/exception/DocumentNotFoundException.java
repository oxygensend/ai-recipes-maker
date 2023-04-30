package com.example.airecipesmaker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DocumentNotFoundException extends AbstractException {
    public DocumentNotFoundException(String message) {
        super(message);
    }
}
