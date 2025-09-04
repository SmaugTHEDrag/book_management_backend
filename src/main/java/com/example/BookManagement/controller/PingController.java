package com.example.BookManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Simple controller to check if the backend service is running.
 * Provides a "ping" endpoint for health checks or quick testing.
 */
@RestController
@RequestMapping("api/")
public class PingController {

    /**
     * GET /api/ping
     * @return a simple "OK" string to indicate the service is alive
     */
    @GetMapping("/ping")
    public String ping(){
        return "OK";
    }
}
