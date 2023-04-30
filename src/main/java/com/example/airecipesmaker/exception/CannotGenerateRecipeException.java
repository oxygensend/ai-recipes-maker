package com.example.airecipesmaker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CannotGenerateRecipeException extends AbstractException {
    public CannotGenerateRecipeException(String message) {
        super(message);
    }

}
