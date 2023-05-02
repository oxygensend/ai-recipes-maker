package com.example.airecipesmaker.exception.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
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
