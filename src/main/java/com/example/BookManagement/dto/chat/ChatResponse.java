package com.example.BookManagement.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/*
 * Data Transfer Object (DTO) for representing the response from the chatbot or chat system
 * Contains the message content, timestamp, and error details
 */
@Data
public class ChatResponse {

    // The actual message return from the chatbot or system
    private String content;

    // The time when this response is created
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    // Indicates whether this response contains error
    private boolean isError;

    // Error details if error occur
    private String errorMessage;

    /*
     * Constructor for a successful response
     */
    public ChatResponse(String content) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isError = false;
    }

    /*
     * Constructor for error or special response
     */
    public ChatResponse(String content, boolean isError, String errorMessage) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isError = isError;
        this.errorMessage = errorMessage;
    }
}
