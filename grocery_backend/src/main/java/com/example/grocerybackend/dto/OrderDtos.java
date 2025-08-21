package com.example.grocerybackend.dto;

import com.example.grocerybackend.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Collection of DTOs for order endpoints.
 */
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Order related DTOs")
public class OrderDtos {

    // PUBLIC_INTERFACE
    @Schema(description = "Order item details")
    public record OrderItemResponse(
            @Schema(description = "Order item ID")
            Long id,
            @Schema(description = "Product ID")
            Long productId,
            @Schema(description = "Product name at time of order")
            String nameSnapshot,
            @Schema(description = "Unit price at time of order")
            BigDecimal unitPrice,
            @Schema(description = "Quantity ordered")
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
