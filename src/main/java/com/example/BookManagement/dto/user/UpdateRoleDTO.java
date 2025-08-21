package com.example.BookManagement.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/*
 * Data Transfer Object (DTO) for updating user's role
 * Used when ADMIN wants to change the role of a user
 */
@Data
public class UpdateRoleDTO {

    // Update Role, only ADMIN or CUSTOMER are allowed
    @NotBlank(message = "Role can not be blank")
    @Pattern(regexp = "ADMIN|CUSTOMER", message = "Role must be either ADMIN or CUSTOMER")
    private String role;
}
