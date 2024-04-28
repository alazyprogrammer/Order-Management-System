package com.licious.ordermanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.licious.ordermanagementsystem.factory.OrderDetailsFactory;
import com.licious.ordermanagementsystem.factory.OrderFactory;
import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderDetails;
// import com.licious.ordermanagementsystem.repository.OrderRepository;
import com.licious.ordermanagementsystem.utils.OrderProcessor;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    // private OrderRepository orderRepository;
    private OrderProcessor orderProcessor;

    @Autowired
    public void setOrderProcessor(OrderProcessor orderProcessor) {
        this.orderProcessor = orderProcessor;
    }

    @Override
    public Order createOrder(OrderDetails orderDetails) {
        // Process the order and enqueue it for background processing
        // Example: Save the order to the database and then enqueue it
        // orderRepository.save(order);
        // Convert DTO to domain model
        OrderDetails orderDetailsWithOrderId = OrderDetailsFactory.createOrderDetails(orderDetails);
        Order order = OrderFactory.createOrder(orderDetailsWithOrderId);
        // Save order to concurrent data structure
        orderProcessor.enqueueOrder(order);
        return order;
    }
}

