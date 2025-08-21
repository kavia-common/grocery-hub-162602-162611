package com.example.grocerybackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Collection of DTOs for authentication endpoints.
 */
public class AuthDtos {

    // PUBLIC_INTERFACE
    public record RegisterRequest(
            @NotBlank(message = "Name is required")
            String name,
            @NotBlank(message = "Email is required")
            @Email(message = "Email must be valid")
            String email,
            @NotBlank(message = "Password is required")
            @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
            String password
    ) {}

    // PUBLIC_INTERFACE
    public record LoginRequest(
            @NotBlank(message = "Email is required")
            @Email(message = "Email must be valid")
            String email,
            @NotBlank(message = "Password is required")
            String password
    ) {}

    // PUBLIC_INTERFACE
    public record AuthResponse(
            String token,
            String tokenType,
            Long userId,
            String name,
            String email
    ) {}
}
