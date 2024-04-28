package com.licious.ordermanagementsystem.repository;

import com.licious.ordermanagementsystem.model.Order;

public interface OrderRepository {
    void save(Order order);
    // Other repository methods
}

