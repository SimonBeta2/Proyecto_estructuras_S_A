package com.example.demo.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDuplicate(DataIntegrityViolationException ex) {

        Map<String, String> response = new HashMap<>();
        String message = ex.getRootCause().getMessage().toLowerCase();

        if (message.contains("unique_email")) {
            response.put("error", "El email ya está registrado");
        } else if (message.contains("unique_telefono")) {
            response.put("error", "El teléfono ya está registrado");
        } else {
            response.put("error", "Error de integridad en la base de datos");
        }

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
        errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}