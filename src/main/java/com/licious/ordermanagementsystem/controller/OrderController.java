package com.licious.ordermanagementsystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.licious.ordermanagementsystem.database.Database;
import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderStatus;
import com.licious.ordermanagementsystem.model.Product;
import com.licious.ordermanagementsystem.model.api.OrderRequest;
import com.licious.ordermanagementsystem.model.api.OrderStatusUpdateRequest;
import com.licious.ordermanagementsystem.service.OrderService;
import com.licious.ordermanagementsystem.utils.OrderValidations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.NonNull;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private Database database;

    // API endpoint for creating an order
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@NonNull @RequestBody OrderRequest createOrderRequest) {
        try {
            // Validate customerId
            ResponseEntity<String> validationResponse = OrderValidations.validateCustomerId(createOrderRequest.getCustomerId());
            if(validationResponse != null) return validationResponse;
            // Validate products map
            validationResponse = OrderValidations.validateProducts(createOrderRequest.getProducts());
            if(validationResponse != null) return validationResponse;
            // Validate each product ID and quantity
            validationResponse = validateEachProduct(createOrderRequest.getProducts());
            if(validationResponse != null) return validationResponse;
            // Check if the customer exists
            validationResponse = validateCustomerExistence(createOrderRequest.getCustomerId());
            if(validationResponse != null) return validationResponse;
            Order createdOrder = orderService.createOrder(createOrderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order: " + e.getMessage());
        }
    }

    // API endpoint for retrieving order details by orderId
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable String orderId) {
        // Validate orderId
        ResponseEntity<String> validationResponse = OrderValidations.validateOrderId(orderId);
        if (validationResponse != null) return validationResponse;
        // Order not found in processed orders, retrieve from database
        Order order = orderService.retrieveOrder(orderId);
        if (order == null) {
            logger.warn("Order with ID {} not found", orderId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + orderId + " not found");
        }

        logger.info("Retrieved order details for order with ID {}", orderId);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
    
    // API endpoint for updating status of an order by orderId
    @PutMapping("/status")
    public ResponseEntity<?> updateOrderStatus(@NonNull @RequestBody OrderStatusUpdateRequest orderStatusUpdateRequest) {
        try {
            // Extract orderId, customerId, and status from the update request
            String orderId = orderStatusUpdateRequest.getOrderId();
            String customerId = orderStatusUpdateRequest.getCustomerId();
            OrderStatus status = orderStatusUpdateRequest.getStatus();

            // Validate orderId, customerId, and status
            ResponseEntity<String> validationResponse = OrderValidations.validateOrderId(orderId);
            if (validationResponse != null) return validationResponse;
            validationResponse = OrderValidations.validateCustomerId(customerId);
            if (validationResponse != null) return validationResponse;
            validationResponse = OrderValidations.validateOrderStatus(status);
            if (validationResponse != null) return validationResponse;
            // Validate order
            Order order = orderService.retrieveOrder(orderId);
            validationResponse = OrderValidations.validateOrderExistence(order, orderId);
            if (validationResponse != null) return validationResponse;
            // Validate User 
            validationResponse = validateCustomerExistence(customerId);
            if(validationResponse != null) return validationResponse;
            // Valid state update
            validationResponse = OrderValidations.validateStatusTransitionAndUpdate(order.getOrderStatus(), status);
            // Update the order status
            Order updatedOrder = orderService.updateOrderStatus(orderId, status);

            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update order status: " + e.getMessage());
        }
    }

    // API endpoint for cancelling an order by orderId
    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable String orderId) {
        try {
            // Check if order exists
            ResponseEntity<String> validationResponse = OrderValidations.validateOrderId(orderId);
            if (validationResponse != null) return validationResponse;
            // Check if order is valid
            Order order = orderService.retrieveOrder(orderId);
            validationResponse = OrderValidations.validateOrderExistence(order, orderId);
            if (validationResponse != null) return validationResponse;
            // Check if order is valid for cancellation
            validationResponse = OrderValidations.validateStatusCancelation(orderId, order.getOrderStatus());
            if(validationResponse != null) return validationResponse;
            // Call the order service to cancel the order
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok("Order with ID " + orderId + " has been cancelled successfully.");
        } catch (IllegalArgumentException e) {
            // Input Validation
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid orderId: " + e.getMessage());
        } catch (Exception e) {
            // 5. Error Handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to cancel order with ID " + orderId + ": " + e.getMessage());
        }
    }

    private ResponseEntity<String> validateCustomerExistence(String customerId) {
        if (!database.getCustomersMap().containsKey(customerId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer with ID " + customerId + " does not exist");
        }
        return null;
    }

    private ResponseEntity<String> validateEachProduct(Map<Long, Integer> products) {
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
        return null;
    }

}
