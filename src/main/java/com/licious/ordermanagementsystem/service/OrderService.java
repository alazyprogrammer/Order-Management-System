package com.licious.ordermanagementsystem.service;

import org.springframework.stereotype.Service;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderDetails;

@Service
public interface OrderService {
    Order createOrder(OrderDetails orderDetails);
    
    // Other service methods
}
