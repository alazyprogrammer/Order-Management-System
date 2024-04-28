package com.licious.ordermanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.licious.ordermanagementsystem.factory.OrderFactory;
import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderOperation;
import com.licious.ordermanagementsystem.repository.OrderRepository;
import com.licious.ordermanagementsystem.utils.OrderProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderProcessor orderProcessor;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        // Process the order and enqueue it for background processing
        // Convert DTO to domain model
        Order orderWithId = OrderFactory.generateOrderId(order);
        Order orderWithStatus = OrderFactory.updateStatus(orderWithId);
        // Save order to concurrent data structure
        logger.info("Adding Order to Order Queue");
        orderProcessor.enqueueOrder(orderWithStatus, OrderOperation.CREATE);
        return order;
    }

    @Override
    public Order retrieveOrder(String orderId, String cusomterId) {
        Order order = OrderFactory.createOrder(orderId, cusomterId);
        // Retrieving data for an order
        logger.info("Retrieving details of order with ID: {}", orderId);
        return orderRepository.retrieve(order);
    }

}

