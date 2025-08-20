package com.example.BookManagement.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatResponse {
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private boolean isError;
    private String errorMessage;

    public ChatResponse(String content) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isError = false;
    }

    public ChatResponse(String content, boolean isError, String errorMessage) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isError = isError;
        this.errorMessage = errorMessage;
    }
}
