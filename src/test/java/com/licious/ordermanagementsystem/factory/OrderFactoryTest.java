package com.licious.ordermanagementsystem.factory;

import org.junit.jupiter.api.Test;

import com.licious.ordermanagementsystem.model.Order;

import static org.junit.jupiter.api.Assertions.*;

public class OrderFactoryTest {

    @Test
    public void testCreateOrder() {
        // Test data
        Order orderDetails = new Order();
        // Set order details data

        // Call the factory method to create an order
        Order order = OrderFactory.createOrder(orderDetails);

        // Assertions
        assertNotNull(order);
        // Add more assertions based on the expected behavior of the factory method
    }
}

