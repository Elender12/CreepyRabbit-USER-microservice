package com.ecirstea.user.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
@RestController
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {


    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());

        return new ResponseEntity<>(body, headers, status);

    }

    @ExceptionHandler(UserException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(UserException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date().getTime(),
                    ex.getCode().value(),
                    ex.getMessage()
            );
        return new ResponseEntity<>(exceptionResponse, ex.getCode());
    }

}
