package com.example.BookManagement.service.aimoderation;

import com.example.BookManagement.exception.ToxicContentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIModerationService implements IAIModerationService{
    private final RestTemplate restTemplate;
    private static final String AI_MODERATION_URL = "http://localhost:8000/moderate";

    @Override
    public void checkComment(String comment){
        try {
            restTemplate.postForEntity(AI_MODERATION_URL, Map.of("text", comment), Void.class);
        } catch(HttpClientErrorException.BadRequest e){
            throw new ToxicContentException(HttpStatus.BAD_REQUEST,"Review contains inappropriate content");
        }
    }
}
