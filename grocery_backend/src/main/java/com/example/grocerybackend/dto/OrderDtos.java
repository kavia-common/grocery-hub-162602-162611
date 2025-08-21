package com.example.grocerybackend.dto;

import com.example.grocerybackend.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Collection of DTOs for order endpoints.
 */
public class OrderDtos {

    // PUBLIC_INTERFACE
    public record OrderItemResponse(
            Long id,
            Long productId,
            String nameSnapshot,
            BigDecimal unitPrice,
            Integer quantity
    ) {}

    // PUBLIC_INTERFACE
    public record OrderResponse(
            Long id,
            List<OrderItemResponse> items,
            BigDecimal total,
            OrderStatus status,
            Instant createdAt
    ) {}

    // PUBLIC_INTERFACE
    public record UpdateOrderStatusRequest(
            @NotNull(message = "Status is required")
            OrderStatus status
    ) {}
}
