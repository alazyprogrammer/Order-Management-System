package com.licious.ordermanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.licious.ordermanagementsystem.factory.OrderFactory;
import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderOperation;
import com.licious.ordermanagementsystem.model.OrderStatus;
import com.licious.ordermanagementsystem.model.api.OrderRequest;
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
    public Order createOrder(OrderRequest orderRequest) {
        // Process the order and enqueue it for background processing
        // Convert DTO to domain model
        Order initialOrder = OrderFactory.initializeOrder(orderRequest);
        // Save order to concurrent data structure
        logger.info("Adding Order to Order Queue");
        orderProcessor.enqueueOrder(initialOrder, OrderOperation.CREATE);
        return initialOrder;
    }

    @Override
    public Order retrieveOrder(String orderId) {
        // Retrieving data for an order
        logger.info("Retrieving details of order with ID: {}", orderId);
        return orderRepository.retrieve(orderId);
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus orderStatus) {
        // Updating status for an order
        Order existingOrder = orderRepository.retrieve(orderId);
        logger.info("Updating status to {} of order with ID: {}", orderStatus, orderId);
        Order updatedOrder = OrderFactory.updateStatus(existingOrder, orderStatus);
        orderProcessor.enqueueOrder(updatedOrder, OrderOperation.UPDATE);
        return updatedOrder;
    }

    @Override
    public Order cancelOrder(String orderId) {
        // Cancelling an order
        logger.info("Cancelling the order with ID: {}", orderId);
        return this.updateOrderStatus(orderId, OrderStatus.CANCELLED);
    }

}

