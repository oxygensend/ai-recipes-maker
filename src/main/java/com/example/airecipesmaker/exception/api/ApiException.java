package com.example.airecipesmaker.exception.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiException {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<AbstractApiSubException> subExceptions;

    private ApiException() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiException(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiException(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public ApiException(HttpStatus status, Throwable exception) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = exception.getLocalizedMessage();
    }

    public ApiException(HttpStatus status, String message, Throwable exception) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = exception.getLocalizedMessage();
    }

    private void addSubException(AbstractApiSubException subError) {
        if (subExceptions == null) {
            subExceptions = new ArrayList<>();
        }
        subExceptions.add(subError);
    }

    private void addValidationException(String object, String field, Object rejectedValue, String message) {
        addSubException(new ApiValidationException(object, field, rejectedValue, message));
    }

    private void addValidationException(String object, String message) {
        addSubException(new ApiValidationException(object, message));
    }

    private void addValidationException(FieldError fieldError) {
        this.addValidationException(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    private void addValidationException(ObjectError objectError) {
        this.addValidationException(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationException(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationException);
    }


    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationException);
    }

}
