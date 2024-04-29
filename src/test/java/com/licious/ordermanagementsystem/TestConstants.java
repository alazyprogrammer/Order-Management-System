package com.licious.ordermanagementsystem;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderStatus;
import com.licious.ordermanagementsystem.model.api.OrderRequest;

public class TestConstants {
    // Customer constants
    public static final String VALID_CUSTOMER_ID = "1234567890";
    public static final String INVALID_CUSTOMER_ID = "";
    public static final String NON_EXISTING_CUSTOMER_ID = "nonExistingCustomer";

    // Order Constants
    public static final String VALID_ORDER_ID = "VALID_ORDER_ID";
    public static final String INVALID_ORDER_ID = "";
    public static final String NON_EXISTING_ORDER_ID = "NON_EXISTING_ORDER_ID";

    // Product constants
    public static final long EXISTING_PRODUCT_ID = 1L;
    public static final long NON_EXISTING_PRODUCT_ID = 100L;

    // Product stock constants
    public static final int AVAILABLE_STOCK = 5;
    public static final int INSUFFICIENT_STOCK = 20;

    // Product prices
    public static final double PRODUCT_PRICE = 100.0;

    // Shipping charge
    public static final double SHIPPING_CHARGE = 45.0;

    // Sub total price
    public static final double SUB_TOTAL = 150.0;

    // Valid order details
    public static final Order VALID_ORDER_DETAILS = createValidOrder();

    // Valid order request
    public static final OrderRequest VALID_ORDER_REQUEST = createValidOrderRequest();

    // Utility method to create valid order details
    private static Order createValidOrder() {
        Order order = new Order();
        order.setCustomerId(VALID_CUSTOMER_ID);
        order.setOrderId(VALID_ORDER_ID);
        Map<Long, Integer> products = new HashMap<>();
        products.put(EXISTING_PRODUCT_ID, 2);
        order.setProducts(products);
        order.setOrderStatus(OrderStatus.CREATED);
        return order;
    }

    // Utitily method to create valid order request
    private static OrderRequest createValidOrderRequest() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId(VALID_CUSTOMER_ID);
        Map<Long, Integer> products = new HashMap<>();
        products.put(EXISTING_PRODUCT_ID, 2);
        orderRequest.setProducts(products);
        orderRequest.setShippingCharge(SHIPPING_CHARGE);
        orderRequest.setSubTotal(SUB_TOTAL);
        orderRequest.setOrderDate(LocalDateTime.now());
        orderRequest.setPaymentStatus(false);
        return orderRequest;
    }

}

