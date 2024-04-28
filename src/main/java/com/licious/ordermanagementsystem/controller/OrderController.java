package com.licious.ordermanagementsystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.licious.ordermanagementsystem.database.Database;
import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.Product;
import com.licious.ordermanagementsystem.service.OrderService;

import lombok.NonNull;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private Database database;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@NonNull @RequestBody Order order) {
        try {
            // Validate customerId
            if (order.getCustomerId() == null || order.getCustomerId().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer ID is required");
            }
            // Validate products map
            Map<Long, Integer> products = order.getProducts();
            if (products == null || products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Products are is required");
            }
            // Validate each product ID and quantity
            for (Map.Entry<Long, Integer> entry : products.entrySet()) {
                Long productId = entry.getKey();
                Integer quantity = entry.getValue();
                if (productId == null || quantity == null || quantity <= 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product ID or quantity");
                }

                // Additional business rule validations
                if (!database.getProductsMap().containsKey(productId)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product with ID " + productId + " does not exist");
                }

                Product product = database.getProductsMap().get(productId);
                if (product.getStock() < quantity) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough stock for product with ID " + productId);
                }
            }
            // Check if the customer exists
            if (!database.getCustomersMap().containsKey(order.getCustomerId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer with ID " + order.getCustomerId() + " does not exist");
            }
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order: " + e.getMessage());
        }
    }
    
    // Other CRUD endpoints
}
