package com.licious.ordermanagementsystem.utils;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderStatus;

public class OrderValidations {
    
    public static ResponseEntity<String> validateOrderId(String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("orderId cannot be null or empty.");
        }
        return null;
    }

    public static ResponseEntity<String> validateOrderExistence(Order order, String orderId) {
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order with ID " + orderId + " not found.");
        }
        return null;
    }

    public static ResponseEntity<String> validateStatusCancelation(String orderId, OrderStatus orderStatus) {
        if (orderStatus != OrderStatus.PENDING || orderStatus != OrderStatus.CREATED) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Order with ID " + orderId + " cannot be cancelled because it is already processed.");
        }
        return null;
    }

    public static ResponseEntity<String> validateCustomerId(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("customerId cannot be null or empty.");
        }
        return null;
    }

    public static ResponseEntity<String> validateOrderStatus(OrderStatus orderStatus) {
        if (orderStatus != OrderStatus.CREATED &&
            orderStatus != OrderStatus.PENDING &&
            orderStatus != OrderStatus.SHIPPED &&
            orderStatus != OrderStatus.DELIVERED &&
            orderStatus != OrderStatus.CANCELLED) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid order status: " + orderStatus);
        }
        return null;
    }

    public static ResponseEntity<String> validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        // Your logic to determine if the status transition is allowed
        // Example: If currentStatus is "pending", allow transition to "processing"
        if (!isValidStatusTransition(currentStatus, newStatus)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid status transition.");
        }
        return null;
    }

    public static ResponseEntity<String> validateProducts(Map<Long, Integer> products) {
        if (products == null || products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Products is/are required");
        }
        return null;
    }

    public static ResponseEntity<String> validateStatusTransitionAndUpdate(OrderStatus currentStatus, OrderStatus newStatus) {
        if (!isValidStatusTransition(currentStatus, newStatus)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid status transition from " + currentStatus + " to " + newStatus);
        }
        return null;
    }

    private static boolean isValidStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        // Your logic to determine if the status transition is allowed
        // Example: If EcurrentStatus is "pending", allow transition to "processing"
        switch (currentStatus) {
            case CREATED:
                return newStatus == OrderStatus.PENDING;
            case PENDING:
                return newStatus == OrderStatus.SHIPPED || newStatus == OrderStatus.CANCELLED;
            case SHIPPED:
                return newStatus == OrderStatus.DELIVERED;
            case DELIVERED:
                // No further transitions allowed
                return false;
            case CANCELLED:
                // No further transitions allowed
                return false;
            default:
                // Unknown current status
                return false;
        }
    }
}
