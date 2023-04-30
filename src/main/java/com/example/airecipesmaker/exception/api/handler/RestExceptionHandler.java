package com.example.airecipesmaker.exception.api.handler;

import com.example.airecipesmaker.exception.AbstractException;
import com.example.airecipesmaker.exception.api.ApiException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Logger;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = Logger.getLogger(RestExceptionHandler.class.getName());

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

    private ResponseEntity<Object> buildResponseEntity(ApiException apiException) {
        this.logger.warning("Throwing an exception: " + apiException);
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }
}
