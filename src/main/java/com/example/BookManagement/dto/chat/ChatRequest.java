package com.example.BookManagement.dto.chat;

import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {
    private String message;
    private List<ChatHistory> chatHistory;
    private FileData fileData;

    @Data
    public static class ChatHistory {
        private String role; // "user" or "model"
        private List<Part> parts;

        @Data
        public static class Part {
            private String text;
            private InlineData inline_data;
        }

        @Data
        public static class InlineData {
            private String mime_type;
            private String data;
        }
    }

    @Data
    public static class FileData {
        private String mime_type;
        private String data;
    }
}
