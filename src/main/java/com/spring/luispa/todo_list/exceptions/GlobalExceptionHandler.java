package com.spring.luispa.todo_list.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.spring.luispa.todo_list.dtos.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = messageSource.getMessage(
                "error.validation",
                null,
                "Error in validation.",
                request.getLocale());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMsg = error.getDefaultMessage();
            errors.put(fieldName, errorMsg);
        });

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request) {
        String errorMessage = ex.getMessage();

        if (errorMessage == null || errorMessage.trim().isEmpty()) {
            errorMessage = messageSource.getMessage("error.not_found",
                    null,
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    request.getLocale());
        }

        return buildErrorResponse(HttpStatus.NOT_FOUND, errorMessage);
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class, IllegalArgumentException.class })
    public ResponseEntity<Object> handleBadRequestExceptions(Exception ex, WebRequest request) {
        String errorMessage = ex.getMessage();

        // if (errorMessage != null && errorMessage.contains("java.time.LocalDateTime"))
        // {
        // errorMessage = messageSource.getMessage("eror.dateTime.format",
        // null,
        // "Invalid datetime format",
        // request.getLocale());
        // }

        if (errorMessage == null || errorMessage.trim().isEmpty()) {
            errorMessage = messageSource.getMessage("error.bad_request",
                    null,
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    request.getLocale());
        }

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtException(Exception ex, WebRequest request) {
        String errorMessage = messageSource.getMessage("error.server",
                null,
                "An unexpected error ocurred.",
                request.getLocale());

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {

        return ResponseEntity.status(status)
                .body(new ApiErrorResponse(status, message));
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message, Map<String, String> errors) {

        return ResponseEntity.status(status)
                .body(new ApiErrorResponse(status, message, errors));
    }

}
