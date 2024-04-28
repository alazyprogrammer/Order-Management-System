package com.licious.ordermanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.licious.ordermanagementsystem.factory.OrderDetailsFactory;
import com.licious.ordermanagementsystem.factory.OrderFactory;
import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderDetails;
import com.licious.ordermanagementsystem.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(OrderDetails orderDetails) {
        // Convert DTO to domain model
        OrderDetails orderDetailsWithOrderId = OrderDetailsFactory.createOrderDetails(orderDetails);
        Order order = OrderFactory.createOrder(orderDetailsWithOrderId);
        // Save order to concurrent data structure
        orderRepository.save(order);
        return order;
    }

    // Other service methods
}

