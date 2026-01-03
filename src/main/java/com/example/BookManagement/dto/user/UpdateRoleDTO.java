package com.example.BookManagement.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateRoleDTO {

    @NotBlank(message = "Role can not be blank")
    @Pattern(regexp = "ADMIN|CUSTOMER", message = "Role must be either ADMIN or CUSTOMER")
    private String role;
}
