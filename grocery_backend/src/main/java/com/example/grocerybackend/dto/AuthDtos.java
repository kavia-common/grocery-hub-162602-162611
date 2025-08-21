package com.example.grocerybackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Collection of DTOs for authentication endpoints.
 */
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication related DTOs")
public class AuthDtos {

    // PUBLIC_INTERFACE
    @Schema(description = "Request payload for user registration")
    public record RegisterRequest(
            @Schema(description = "User's full name", example = "John Doe")
            @Schema(description = "User's full name", example = "John Doe")
            @NotBlank(message = "Name is required")
            String name,

            @Schema(description = "User's email address", example = "john.doe@example.com")
            @NotBlank(message = "Email is required")
            @Email(message = "Email must be valid")
            String email,

            @Schema(description = "User's password (min 6 characters)", example = "password123")
            @NotBlank(message = "Password is required")
            @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
            String password
    ) {}

    // PUBLIC_INTERFACE
    @Schema(description = "Request payload for user login")
    public record LoginRequest(
            @Schema(description = "User's email address", example = "john.doe@example.com")
            @NotBlank(message = "Email is required")
            @Email(message = "Email must be valid")
            String email,

            @Schema(description = "User's password", example = "password123")
            @NotBlank(message = "Password is required")
            String password
    ) {}

    // PUBLIC_INTERFACE
    @Schema(description = "Response payload for authentication operations")
    public record AuthResponse(
            @Schema(description = "JWT token for authentication", example = "eyJhbGciOiJIUzI1NiIs...")
            String token,
            @Schema(description = "Token type", example = "Bearer")
            String tokenType,
            @Schema(description = "User's ID", example = "1")
            Long userId,
            @Schema(description = "User's name", example = "John Doe")
            String name,
            @Schema(description = "User's email", example = "john.doe@example.com")
            String email
    ) {}
}
