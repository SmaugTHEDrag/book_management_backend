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

    // Return "OK" if the service is running
    @GetMapping("/ping")
    public String ping(){
        return "OK";
    }
}
