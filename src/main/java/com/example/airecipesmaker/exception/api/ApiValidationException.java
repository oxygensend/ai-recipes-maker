package com.example.airecipesmaker.exception.api;

public class ApiValidationException extends AbstractApiSubException {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationException(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
