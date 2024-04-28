package com.licious.ordermanagementsystem.service;

import org.springframework.stereotype.Service;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderStatus;

@Service
public interface OrderService {

    Order createOrder(Order order);
    Order retrieveOrder(String orderId);
    Order updateOrderStatus(String orderId, OrderStatus orderStatus);
    Order cancelOrder(String orderId);

}
