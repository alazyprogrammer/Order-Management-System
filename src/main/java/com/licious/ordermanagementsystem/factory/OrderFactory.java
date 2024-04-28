package com.licious.ordermanagementsystem.factory;

import java.util.UUID;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderStatus;

public class OrderFactory {
    public static Order createOrder(Order order) {
        // Create and return a new Order instance
        // Generate a UUID for the orderId
        UUID orderId = UUID.randomUUID();
        // Create and return a new OrderDetails instance with orderId
        order.setOrderId(orderId.toString());
        // Update order status
        OrderStatus orderStatus = order.isPaymentStatus() ? OrderStatus.CREATED : OrderStatus.PENDING;
        order.setOrderStatus(orderStatus);
        return order;
    }
}

