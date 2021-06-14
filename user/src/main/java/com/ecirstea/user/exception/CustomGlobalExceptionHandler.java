package com.ecirstea.user.exception;


import com.google.common.base.Splitter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@RestController
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {


    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        String error;
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());

        //If: It means that the error is not from @Valid, but from custom validation. Else: error from @Valid
        if (ex.getBindingResult().getFieldErrors().isEmpty()) {
            error = Objects.requireNonNull(ex.getBindingResult().getGlobalError()).getDefaultMessage();
            body.put("field", "name");
            body.put("problem", "duplicated");
        } else {
            error = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
            body.put("field", ex.getBindingResult().getFieldError().getField());

            if (Objects.equals(ex.getBindingResult().getFieldError().getCode(), "NotBlank")) {
                body.put("problem", "empty");
            }
            if (ex.getBindingResult().getFieldError().getCode().equals("NotNull")) {
                body.put("problem", "null");
            }
        }

        body.put("message", error);
        body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());
        logger.error("Error Message ::: " + error);

        return new ResponseEntity<>(body, headers, status);

    }

    @ExceptionHandler(UserException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(UserException ex, WebRequest request) {
        String pathWithoutURIWord = request.getDescription(false).substring(request.getDescription(false).indexOf("=") + 1);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ex.getCode().value(),
                ex.getCode().getReasonPhrase(),
                ex.getErrorField(),
                "error",
                ex.getMessage(),
                pathWithoutURIWord);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    private Map<String, String> bodyStrToMap(String clientMessageStr) {
        String bodyWithoutTimestamp = clientMessageStr.substring(clientMessageStr.indexOf(",") + 1)
                .replace("\"", "");
        String result = bodyWithoutTimestamp.substring(0, bodyWithoutTimestamp.length() - 1);
        return Splitter.on(",").withKeyValueSeparator(':').split(result);
    }


}
