package com.example.BookManagement.dto.chat;

import lombok.Data;

import java.util.List;

/*
 * Data Transfer Object (DTO) representing a chat request
 * This object is sent from the client to the backend for processing chat messages
 * including message history and optional file attachments
 */
@Data
public class ChatRequest {

    // The current message from the user
    private String message;

    // List of past chat messages for conversation context
    private List<ChatHistory> chatHistory;

    // Optional file data sent along with the message
    private FileData fileData;

    /*
     * Represents a single chat history entry
     * Each history contains a role (user or model) and a list of message part
     */
    @Data
    public static class ChatHistory {

        // Role of the message sender - USER or MODEL
        private String role;

        // The list of parts (text or inline data) that make up this message
        private List<Part> parts;

        /*
         * Represents a part of a message
         *  A part can contain plain text or inline binary data
         */
        @Data
        public static class Part {

            // The text content of this part
            private String text;

            // Inline binary data
            private InlineData inline_data;
        }

        /*
         * Represents inline binary data within a message
         * Typically used for sending images or documents
         */
        @Data
        public static class InlineData {

            // MINE type of data (image/png, application/pdf)
            private String mime_type;

            // Base64 encoded binary data
            private String data;
        }
    }

    /*
     * Represents file data attached to the chat request
     * Used for sending entire files along with the current message
     */
    @Data
    public static class FileData {

        // MINE type of the attached file
        private String mime_type;

        // Base64 encoded file data
        private String data;
    }
}
