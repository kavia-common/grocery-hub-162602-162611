package com.example.grocerybackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Collection of DTOs for product endpoints.
 */
public class ProductDtos {

    // PUBLIC_INTERFACE
    public record ProductResponse(
            Long id,
            String name,
            String description,
            String category,
            BigDecimal price,
            String imageUrl,
            Integer stock
    ) {}

    // PUBLIC_INTERFACE
    public record CreateProductRequest(
            @NotBlank(message = "Name is required")
            String name,

            String description,

            @NotBlank(message = "Category is required")
            String category,

            @NotNull(message = "Price is required")
            @Min(value = 0, message = "Price must be non-negative")
            BigDecimal price,

            String imageUrl,

            @NotNull(message = "Stock is required")
            @Min(value = 0, message = "Stock must be non-negative")
            Integer stock
    ) {}

    // PUBLIC_INTERFACE
    public record UpdateProductRequest(
            String name,
            String description,
            String category,
            @Min(value = 0, message = "Price must be non-negative")
            BigDecimal price,
            String imageUrl,
            @Min(value = 0, message = "Stock must be non-negative")
            Integer stock
    ) {}
}
