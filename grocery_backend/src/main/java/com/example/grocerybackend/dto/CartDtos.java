package com.example.grocerybackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

/**
 * Collection of DTOs for cart endpoints.
 */
public class CartDtos {

    // PUBLIC_INTERFACE
    public record CartItemResponse(
            Long id,
            Long productId,
            String productName,
            String productDescription,
            String productImageUrl,
            Integer quantity
    ) {}

    // PUBLIC_INTERFACE
    public record CartResponse(
            Long id,
            List<CartItemResponse> items,
            Instant updatedAt
    ) {}

    // PUBLIC_INTERFACE
    public record AddToCartRequest(
            @NotNull(message = "Product ID is required")
            Long productId,

            @NotNull(message = "Quantity is required")
            @Min(value = 1, message = "Quantity must be at least 1")
            Integer quantity
    ) {}

    // PUBLIC_INTERFACE
    public record UpdateCartItemRequest(
            @NotNull(message = "Quantity is required")
            @Min(value = 1, message = "Quantity must be at least 1")
            Integer quantity
    ) {}
}
