package com.example.BookManagement.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ToxicContentException.class)
    public ResponseEntity<?> handleToxicContent(ToxicContentException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(Map.of(
                        "message", ex.getMessage()
                ));
    }
}
