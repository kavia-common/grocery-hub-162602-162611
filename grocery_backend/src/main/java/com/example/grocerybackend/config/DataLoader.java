package com.example.grocerybackend.config;

import com.example.grocerybackend.model.Product;
import com.example.grocerybackend.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

/**
 * Configuration class to seed initial data into the database.
 */
@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository) {
        return args -> {
            // Only seed if the product table is empty
            if (productRepository.count() == 0) {
                List<Product> products = List.of(
                    new Product(
                        "Fresh Bananas",
                        "Sweet and ripe bananas, perfect for snacking or baking",
                        "Fruits",
                        new BigDecimal("2.99"),
                        "https://example.com/images/bananas.jpg",
                        100
                    ),
                    new Product(
                        "Organic Spinach",
                        "Fresh organic spinach leaves, packed with nutrients",
                        "Vegetables",
                        new BigDecimal("3.49"),
                        "https://example.com/images/spinach.jpg",
                        50
                    ),
                    new Product(
                        "Whole Grain Bread",
                        "Freshly baked whole grain bread, rich in fiber",
                        "Bakery",
                        new BigDecimal("4.99"),
                        "https://example.com/images/bread.jpg",
                        30
                    ),
                    new Product(
                        "Free-Range Eggs",
                        "Farm fresh free-range eggs, dozen pack",
                        "Dairy & Eggs",
                        new BigDecimal("5.99"),
                        "https://example.com/images/eggs.jpg",
                        40
                    ),
                    new Product(
                        "Greek Yogurt",
                        "Creamy Greek yogurt, plain flavor",
                        "Dairy & Eggs",
                        new BigDecimal("4.49"),
                        "https://example.com/images/yogurt.jpg",
                        25
                    ),
                    new Product(
                        "Granny Smith Apples",
                        "Crisp and tart Granny Smith apples",
                        "Fruits",
                        new BigDecimal("0.99"),
                        "https://example.com/images/apples.jpg",
                        75
                    ),
                    new Product(
                        "Ground Coffee",
                        "Premium arabica ground coffee, medium roast",
                        "Beverages",
                        new BigDecimal("12.99"),
                        "https://example.com/images/coffee.jpg",
                        20
                    ),
                    new Product(
                        "Pasta Sauce",
                        "Homestyle Italian pasta sauce",
                        "Pantry",
                        new BigDecimal("3.99"),
                        "https://example.com/images/pasta-sauce.jpg",
                        45
                    ),
                    new Product(
                        "Chicken Breast",
                        "Fresh boneless chicken breast",
                        "Meat & Poultry",
                        new BigDecimal("8.99"),
                        "https://example.com/images/chicken.jpg",
                        30
                    ),
                    new Product(
                        "Atlantic Salmon",
                        "Fresh Atlantic salmon fillet",
                        "Seafood",
                        new BigDecimal("14.99"),
                        "https://example.com/images/salmon.jpg",
                        15
                    )
                );

                productRepository.saveAll(products);
                System.out.println("Sample product data has been loaded");
            }
        };
    }
}
