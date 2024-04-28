package com.licious.ordermanagementsystem.repository;

import com.licious.ordermanagementsystem.model.Order;

public interface OrderRepository {
    void create(Order order);
    Order retrieve(String orderId);
    // Other repository methods
}

