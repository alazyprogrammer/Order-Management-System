package com.licious.ordermanagementsystem.factory;

import java.util.UUID;

import com.licious.ordermanagementsystem.model.OrderDetails;

public class OrderDetailsFactory {
    public static OrderDetails createOrderDetails(OrderDetails orderDetails) {
        // Generate a UUID for the orderId
        UUID orderId = UUID.randomUUID();
        // Create and return a new OrderDetails instance with orderId
        orderDetails.setOrderId(orderId.toString());
        return orderDetails;
    }
}
