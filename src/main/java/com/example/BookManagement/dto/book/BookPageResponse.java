package com.example.BookManagement.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookPageResponse {
    private List<BookDTO> content;
    private int currentPage;
    private long totalItems;
    private int totalPages;
    private int pageSize;
    private boolean isLast;
    private boolean isFirst;
}
