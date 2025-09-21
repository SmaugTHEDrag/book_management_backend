package com.example.BookManagement.controller;

import com.example.BookManagement.dto.chat.ChatRequest;
import com.example.BookManagement.dto.chat.ChatResponse;
import com.example.BookManagement.service.chat.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * REST Controller for handling chat interactions with the Book Assistant.
 * Provides endpoints to generate chat responses and check service health.
 */
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor  // Automatically injects final fields (ChatService)
@Slf4j  // Lombok annotation for logging
@Tag(name = "Chat API", description = "APIs for interacting with the Book Assistant")
public class ChatController {

    // Service layer handling chat logic
    private final ChatService geminiService;

    // Simple health check endpoint to verify that the chat service is running
    @Operation(summary = "Health check", description = "Returns a simple message indicating that the chat service is running")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Chat service is running");
    }

    @Operation(summary = "Generate chat response",
            description = "Generates a response from the Book Assistant based on the user's message")
    // Generates chat response from the Book Assistant based on the user's message
    @PostMapping("/generate")
    public ResponseEntity<ChatResponse> generateChatResponse(@RequestBody ChatRequest chatRequest) {
        log.info("Received chat request: {}", chatRequest.getMessage());

        try {
            ChatResponse response = geminiService.generateResponse(chatRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error generating chat response", e);
            ChatResponse errorResponse = new ChatResponse(
                    "Sorry, I encountered an error while processing your request.",
                    true,
                    e.getMessage()
            );
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

}
