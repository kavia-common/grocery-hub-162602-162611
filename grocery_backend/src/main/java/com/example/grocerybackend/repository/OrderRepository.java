package com.example.grocerybackend.repository;

import com.example.grocerybackend.model.Order;
import com.example.grocerybackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for Order entities.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
}
