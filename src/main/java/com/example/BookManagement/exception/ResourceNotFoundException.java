package com.example.BookManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource (entity) is not found.
 * Maps to HTTP 404 NOT FOUND status automatically.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)

// Constructs a new ResourceNotFoundException with the specified detail message.
public class ResourceNotFoundException extends RuntimeException{

    // Optional constructor to include a cause.
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
