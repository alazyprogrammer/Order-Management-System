package com.licious.ordermanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.licious.ordermanagementsystem.factory.OrderFactory;
import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.utils.OrderProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderProcessor.class);

    @Autowired
    private OrderProcessor orderProcessor;

    @Override
    public Order createOrder(Order order) {
        // Process the order and enqueue it for background processing
        // Example: Save the order to the database and then enqueue it
        // orderRepository.save(order);
        // Convert DTO to domain model
        Order updatedOrder = OrderFactory.createOrder(order);
        // Save order to concurrent data structure
        logger.info("Adding Order to Order Queue");
        orderProcessor.enqueueOrder(updatedOrder);
        return order;
    }
}

