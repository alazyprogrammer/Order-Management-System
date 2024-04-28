package com.licious.ordermanagementsystem.service;

import org.springframework.stereotype.Service;

import com.licious.ordermanagementsystem.model.Order;

@Service
public interface OrderService {

    Order createOrder(Order order);
    Order retrieveOrder(String orderId, String customerId);
    // Other service methods
}
