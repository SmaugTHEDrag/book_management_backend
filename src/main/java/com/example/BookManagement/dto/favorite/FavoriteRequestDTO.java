package com.example.BookManagement.dto.favorite;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FavoriteRequestDTO {
    @NotNull(message = "Book ID can not be null")
    private Integer bookId;
}
