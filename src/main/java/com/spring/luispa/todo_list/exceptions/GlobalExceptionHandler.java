package com.spring.luispa.todo_list.exceptions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.spring.luispa.todo_list.dtos.ApiErrorResponse;
import com.spring.luispa.todo_list.services.MessageService;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private MessageService messageService;

    public GlobalExceptionHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    // === Errores en la validación de DTO's ===

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = messageService.getMessage("error.validations", "error.validations.default");

        // Map<String, String> errors = ex.getBindingResult()
        // .getAllErrors()
        // .stream()
        // .map(error -> (FieldError) error)
        // .collect(Collectors.toMap(FieldError::getField, error ->
        // error.getDefaultMessage()));

        Map<String, List<String>> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> (FieldError) error)
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, errors);
    }

    // === Errores de deserialización JSON o tipos inválidos ===

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleBadRequestExceptions(HttpMessageNotReadableException ex, WebRequest request) {
        String errorMessage = messageService.getMessage("error.input.mismatchType",
                "error.input.mismatchType.default");

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    // === Errores del cliente ===

    @ExceptionHandler(IdMismatchException.class)
    public ResponseEntity<Object> handleIdMismatchException(IdMismatchException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Object> handleClientErrors(ClientException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // === Errores de la lógica de negocio ===

    @ExceptionHandler(InvalidTaskStateTransitionException.class)
    public ResponseEntity<Object> handleInvalidTaskStateTranstionException(InvalidTaskStateTransitionException ex,
            WebRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // === Errores genéricos no controlados ===

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtException(Exception ex, WebRequest request) {
        String errorMessage = messageService.getMessage("error.server",
                "error.server.default");

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    // === Helpers ===

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {

        return ResponseEntity.status(status)
                .body(new ApiErrorResponse(status, message));
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message,
            Map<String, List<String>> errors) {

        return ResponseEntity.status(status)
                .body(new ApiErrorResponse(status, message, errors));
    }

}
