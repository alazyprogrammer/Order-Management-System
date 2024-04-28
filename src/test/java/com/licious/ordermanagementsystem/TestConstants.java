package com.licious.ordermanagementsystem;

import java.util.HashMap;
import java.util.Map;

import com.licious.ordermanagementsystem.model.Order;

public class TestConstants {
    // Customer constants
    public static final String VALID_CUSTOMER_ID = "1234567890";
    public static final String NON_EXISTING_CUSTOMER_ID = "nonExistingCustomer";

    // Product constants
    public static final long EXISTING_PRODUCT_ID = 1L;
    public static final long NON_EXISTING_PRODUCT_ID = 100L;

    // Product stock constants
    public static final int AVAILABLE_STOCK = 5;
    public static final int INSUFFICIENT_STOCK = 20;

    // Product prices
    public static final double PRODUCT_PRICE = 100.0;

    // Valid order details
    public static final Order VALID_ORDER_DETAILS = createValidOrder();

    // Utility method to create valid order details
    private static Order createValidOrder() {
        Order order = new Order();
        order.setCustomerId(VALID_CUSTOMER_ID);
        Map<Long, Integer> products = new HashMap<>();
        products.put(EXISTING_PRODUCT_ID, 2);
        order.setProducts(products);
        return order;
    }

}

