package com.licious.ordermanagementsystem.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.licious.ordermanagementsystem.database.Database;
import com.licious.ordermanagementsystem.model.Order;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final Map<String, Order> ordersMap;

    public OrderRepositoryImpl(Database database) {
        this.ordersMap = database.getOrdersMap();
    }

    @Override
    public void save(Order order) {
        // Save the order to the ordersMap
        ordersMap.put(order.getCustomerId(), order);
    }

    // Other repository methods
}

