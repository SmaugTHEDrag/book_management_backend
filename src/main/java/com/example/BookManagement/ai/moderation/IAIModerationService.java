package com.example.BookManagement.ai.moderation;

public interface IAIModerationService {

    // check toxic content
    void checkComment(String comment, String errorMessage);
}
