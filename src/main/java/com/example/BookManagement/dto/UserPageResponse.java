package com.example.BookManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageResponse {
    private List<UserDTO> content;
    private int currentPage;
    private long totalItems;
    private int totalPages;
    private int pageSize;
    private boolean isLast;
    private boolean isFirst;
}
