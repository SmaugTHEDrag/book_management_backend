package com.example.BookManagement.service.aimoderation;

public interface IAIModerationService {

    // check toxic content
    void checkComment(String comment, String errorMessage);
}
