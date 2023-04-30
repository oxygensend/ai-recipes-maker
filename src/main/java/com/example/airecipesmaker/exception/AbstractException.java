package com.example.airecipesmaker.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractException  extends RuntimeException {
    public AbstractException(String message) {
        super(message);
    }
    public HttpStatus getStatusCode() {
        ResponseStatus responseStatus = this.getClass().getAnnotation(ResponseStatus.class);
        return responseStatus.value();
    }
}
