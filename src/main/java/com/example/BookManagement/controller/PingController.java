package com.example.BookManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Simple controller to check if the backend service is running.
 * Provides a "ping" endpoint for health checks or quick testing.
 */
@RestController
@RequestMapping("api/")
@Tag(name = "Ping API", description = "API for monitoring backend uptime using UptimeRobot")
public class PingController {

    // Return "OK" if the service is running
    @Operation(summary = "Ping backend service", description = "Returns 'OK' to indicate the backend is alive. " +
            "Can be called every 5 minutes to keep Render awake.")
    @GetMapping("/ping")
    public String ping(){
        return "OK";
    }
}
