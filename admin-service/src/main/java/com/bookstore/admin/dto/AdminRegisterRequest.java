package com.bookstore.admin.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AdminRegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private String role; // ADMIN or SUPER_ADMIN
}