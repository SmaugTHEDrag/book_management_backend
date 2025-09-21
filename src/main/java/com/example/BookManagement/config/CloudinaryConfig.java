package com.example.BookManagement.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Configuration class for Cloudinary integration.
 * Loads Cloudinary credentials from application.properties
 * and exposes a Cloudinary bean to be used in services.
 */
@Configuration
public class CloudinaryConfig {

    // // Inject values from application.properties
    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    @Value("${cloudinary.api_key}")
    private String apiKey;

    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    /*
     * Create a Cloudinary bean with the given credentials.
     * This bean can be injected into services that need to upload files.
     */
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }
}
