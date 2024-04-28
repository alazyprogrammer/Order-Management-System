package com.licious.ordermanagementsystem.factory;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderDetails;
import com.licious.ordermanagementsystem.model.OrderStatus;

public class OrderFactory {
    public static Order createOrder(OrderDetails orderDetails) {
        // Create and return a new Order instance
        OrderStatus orderStatus = orderDetails.isPaymentStatus() ? OrderStatus.CREATED : OrderStatus.PENDING;
        return Order.builder().orderId(orderDetails.getOrderId())
                              .customerId(orderDetails.getCustomerId())
                              .orderStatus(orderStatus)
                              .build();
    }
}

