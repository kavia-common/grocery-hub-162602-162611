package com.example.grocerybackend.controller;

import com.example.grocerybackend.dto.CartDtos.*;
import com.example.grocerybackend.model.*;
import com.example.grocerybackend.repository.*;
import com.example.grocerybackend.security.AppUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST controller for managing shopping cart.
 */
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "Endpoints for managing shopping cart")
@SecurityRequirement(name = "Bearer Authentication")
public class CartController {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartController(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    private CartItemResponse toCartItemResponse(CartItem item) {
        return new CartItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getDescription(),
                item.getProduct().getImageUrl(),
                item.getQuantity()
        );
    }

    private CartResponse toCartResponse(Cart cart) {
        return new CartResponse(
                cart.getId(),
                cart.getItems().stream()
                        .map(this::toCartItemResponse)
                        .toList(),
                cart.getUpdatedAt()
        );
    }

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(new Cart(user)));
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "Get cart", description = "Get the current user's shopping cart")
    public CartResponse getCart(@AuthenticationPrincipal AppUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Cart cart = getOrCreateCart(user);
        return toCartResponse(cart);
    }

    // PUBLIC_INTERFACE
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add to cart", description = "Add a product to the shopping cart")
    public CartResponse addToCart(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @Valid @RequestBody AddToCartRequest request) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (product.getStock() < request.quantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock");
        }

        Cart cart = getOrCreateCart(user);
        CartItem cartItem = new CartItem(product, request.quantity());
        cart.addItem(cartItem);
        cart = cartRepository.save(cart);

        return toCartResponse(cart);
    }

    // PUBLIC_INTERFACE
    @PutMapping("/items/{itemId}")
    @Operation(summary = "Update cart item", description = "Update the quantity of a cart item")
    public CartResponse updateCartItem(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Cart cart = getOrCreateCart(user);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));

        if (!item.getCart().equals(cart)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cart item does not belong to user");
        }

        if (item.getProduct().getStock() < request.quantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock");
        }

        item.setQuantity(request.quantity());
        cartItemRepository.save(item);

        return toCartResponse(cart);
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove from cart", description = "Remove an item from the shopping cart")
    public void removeFromCart(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @PathVariable Long itemId) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Cart cart = getOrCreateCart(user);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));

        if (!item.getCart().equals(cart)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cart item does not belong to user");
        }

        cart.removeItem(item);
        cartRepository.save(cart);
    }

    // PUBLIC_INTERFACE
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Clear cart", description = "Remove all items from the shopping cart")
    public void clearCart(@AuthenticationPrincipal AppUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Cart cart = getOrCreateCart(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
