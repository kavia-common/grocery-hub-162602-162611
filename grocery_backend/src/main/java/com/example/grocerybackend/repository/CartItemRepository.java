package com.example.grocerybackend.repository;

import com.example.grocerybackend.model.CartItem;
import com.example.grocerybackend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for CartItem entities.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
}
