package com.example.grocerybackend.repository;

import com.example.grocerybackend.model.OrderItem;
import com.example.grocerybackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for OrderItem entities.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}
