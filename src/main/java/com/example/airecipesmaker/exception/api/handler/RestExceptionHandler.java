package com.example.airecipesmaker.exception.api.handler;

import com.example.airecipesmaker.exception.AbstractException;
import com.example.airecipesmaker.exception.api.ApiException;
import lombok.extern.java.Log;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Log
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(AbstractException.class)
    protected ResponseEntity<Object> handleCustomException(AbstractException ex) {
       ApiException apiException = new ApiException(ex.getStatusCode(), ex.getMessage());
       return buildResponseEntity(apiException);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, "Validation error");
        apiException.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiException.addValidationException(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException apiException) {
        log.warning("Throwing an exception: " + apiException);
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }
}
