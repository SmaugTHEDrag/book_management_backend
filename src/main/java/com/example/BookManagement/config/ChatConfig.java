package com.example.BookManagement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/*
 * Configuration class for chat-related beans.
 * Provides RestTemplate for HTTP calls and ObjectMapper for JSON serialization/deserialization.
 */
@Configuration
public class ChatConfig {
    /*
     * Bean for performing REST API calls.
     * Used in services to call external APIs like Gemini or other HTTP endpoints.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Bean for Jackson ObjectMapper.
     * Configured to handle Java 8 Date/Time types properly and avoid timestamp serialization.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Register JavaTimeModule to handle Java 8 date/time types (LocalDate, LocalDateTime, etc.)
        mapper.registerModule(new JavaTimeModule());

        // Disable writing dates as numeric timestamps, will use ISO-8601 format instead
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}
