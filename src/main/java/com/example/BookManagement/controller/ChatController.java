package com.example.BookManagement.controller;

import com.example.BookManagement.dto.chat.ChatRequest;
import com.example.BookManagement.dto.chat.ChatResponse;
import com.example.BookManagement.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService geminiService;

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

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Chat service is running");
    }
}
