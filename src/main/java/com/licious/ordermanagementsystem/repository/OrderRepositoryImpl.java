package com.licious.ordermanagementsystem.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.licious.ordermanagementsystem.database.Database;
import com.licious.ordermanagementsystem.model.Order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private static final Logger logger = LogManager.getLogger(OrderRepositoryImpl.class);

    private final Map<String, Order> ordersMap;

    public OrderRepositoryImpl(Database database) {
        this.ordersMap = database.getOrdersMap();
    }

    @Override
    public void create(Order order) {
        // Save the order to the ordersMap
        ordersMap.put(order.getOrderId(), order);
    }

    @Override
    public Order retrieve(Order order) {
        // Retrieve the order details
        return ordersMap.get(order.getOrderId());
    }
    // Other repository methods
}

