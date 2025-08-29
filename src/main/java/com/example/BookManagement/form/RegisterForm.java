package com.example.BookManagement.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/*
 * Form object for handling user registration request
 * Contains basic validation rules for username, email, and password
 */
@Data
public class RegisterForm {

    // ---- USERNAME -----

    /*
     * User must:
     * - not be blank
     * - contain 6 to 20 characters
     * - Only contain letters, numbers, underscores
     */
    @NotBlank(message = "Username does not blank")
    @Size(min = 6, max = 20, message = "Username from 6 to 20 letters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers or underscore ('_') ")
    private String username;


    // ---- EMAIL -----

    /*
     * Email must:
     * - Not be blank
     * - Follow a valid email format
     */
    @NotBlank(message = "Email does not blank")
    @Email(message = "Email is valid")
    private String email;

    // ---- PASSWORD -----

    /*
     * Password must:
     * - Not be blank
     * - Contain at least 8 characters
     * - Contain at least 1 uppercase letter
     * - Contain at least 1 number
     * - Contain at least 1 special character (!@#$%^&*()_+-=)
     */
    @NotBlank(message = "Password does not blank")
    @Size(min = 8, message = "Password must have at least 8 letters")
    @Pattern.List({
            @Pattern(regexp = ".*[A-Z].*", message = "Password must have at least 1 upper letter"),
            @Pattern(regexp = ".*\\d.*", message = "Password must have at least 1 number"),
            @Pattern(regexp = ".*[!@#$%^&*()_+\\-=].*", message = "Password must have at least 1 special symbol")
    })
    private String password;

    // ---- ROLE -----

    /*
     * User role (OPTIONAL) ADMIN OR CUSTOMER
     */
    private String role;
}
