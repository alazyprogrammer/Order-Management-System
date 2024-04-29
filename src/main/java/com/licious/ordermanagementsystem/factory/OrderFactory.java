package com.licious.ordermanagementsystem.factory;

import java.util.UUID;

import com.licious.ordermanagementsystem.Mapper.OrderMapper;
import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderStatus;
import com.licious.ordermanagementsystem.model.api.OrderRequest;

public class OrderFactory {

    public static Order initializeOrder(OrderRequest orderRequest) {
        // Create and return a new Order instance
        Order order = OrderMapper.mapOrderRequestToOrder(orderRequest);
        // Generate a UUID for the orderId
        UUID orderId = UUID.randomUUID();
        order.setOrderId(orderId.toString());
        // Update order status
        OrderStatus orderStatus = order.isPaymentStatus() ? OrderStatus.CREATED : OrderStatus.PENDING;
        order.setOrderStatus(orderStatus);
        return order;
    }

    public static Order updateStatus(Order order, OrderStatus orderStatus) {
        order.setOrderStatus(orderStatus);
        return order;
    }

}

