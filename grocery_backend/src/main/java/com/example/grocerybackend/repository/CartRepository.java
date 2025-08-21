package com.example.grocerybackend.repository;

import com.example.grocerybackend.model.Cart;
import com.example.grocerybackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for Cart entities.
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
