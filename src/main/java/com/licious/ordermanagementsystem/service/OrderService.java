package com.licious.ordermanagementsystem.service;

import org.springframework.stereotype.Service;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderDetails;
import com.licious.ordermanagementsystem.utils.OrderProcessor;

@Service
public interface OrderService {

    // Order createOrder(OrderDetails orderDetails);
    void setOrderProcessor(OrderProcessor orderProcessor);
    Order createOrder(OrderDetails orderDetails);
    // Other service methods
}
