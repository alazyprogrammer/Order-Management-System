package com.licious.ordermanagementsystem.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.licious.ordermanagementsystem.TestConstants;
import com.licious.ordermanagementsystem.database.Database;
import com.licious.ordermanagementsystem.model.Customer;
import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderStatus;
import com.licious.ordermanagementsystem.model.Product;
import com.licious.ordermanagementsystem.model.api.OrderRequest;
import com.licious.ordermanagementsystem.model.api.OrderStatusUpdateRequest;
import com.licious.ordermanagementsystem.service.OrderService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private Database database;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void testCreateOrder_Success() {
        // Mock database
        when(database.getCustomersMap()).thenReturn(getMockCustomersMap());
        when(database.getProductsMap()).thenReturn(getMockProductsMap());
        // Mocked order
        Order createdOrder = mock(Order.class);
        when(orderService.createOrder(TestConstants.VALID_ORDER_REQUEST)).thenReturn(createdOrder);

        ResponseEntity<?> response = orderController.createOrder(TestConstants.VALID_ORDER_REQUEST);
        System.out.println("Response: " + response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdOrder, response.getBody());
    }

    @Test
    public void testCreateOrder_MissingCustomerId() {
        OrderRequest request = new OrderRequest();
        // Customer ID is missing

        ResponseEntity<?> response = orderController.createOrder(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Add more assertions for the error message in the response body
    }

    @Test
    public void testGetOrderDetails_ValidOrderId() {
        String orderId = TestConstants.VALID_ORDER_ID;

        // Mock service response
        Order order = mock(Order.class); // Mocked order object
        when(orderService.retrieveOrder(orderId)).thenReturn(order);

        // Execute the method
        ResponseEntity<?> response = orderController.getOrderDetails(orderId);

        // Validate the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testGetOrderDetails_InvalidOrderId() {
        String invalidOrderId = TestConstants.INVALID_ORDER_ID;

        // Execute the method
        ResponseEntity<?> response = orderController.getOrderDetails(invalidOrderId);

        // Validate the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Add assertions for the error message in the response body
    }

    @Test
    public void testGetOrderDetails_OrderNotFound() {
        String orderIdNotFound = TestConstants.NON_EXISTING_ORDER_ID;

        // Mock service response (order not found)
        when(orderService.retrieveOrder(orderIdNotFound)).thenReturn(null);

        // Execute the method
        ResponseEntity<?> response = orderController.getOrderDetails(orderIdNotFound);

        // Validate the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        // Add assertions for the error message in the response body
    }

    @Test
    public void testUpdateOrderStatus_InvalidOrderId() {
        String invalidOrderId = TestConstants.INVALID_ORDER_ID;

        // Execute the method
        OrderStatusUpdateRequest request = new OrderStatusUpdateRequest(TestConstants.VALID_CUSTOMER_ID, invalidOrderId, OrderStatus.SHIPPED);
        ResponseEntity<?> response = orderController.updateOrderStatus(request);

        // Validate the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Add assertions for the error message in the response body
    }

    @Test
    public void testUpdateOrderStatus_InvalidCustomerId() {
        String orderId = TestConstants.VALID_ORDER_ID;
        String invalidCustomerId = TestConstants.INVALID_CUSTOMER_ID;

        // Execute the method
        OrderStatusUpdateRequest request = new OrderStatusUpdateRequest(invalidCustomerId, orderId, OrderStatus.SHIPPED);
        ResponseEntity<?> response = orderController.updateOrderStatus(request);

        // Validate the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Add assertions for the error message in the response body
    }

    // Utility method to create mock customers map
    private static Map<String, Customer> getMockCustomersMap() {
        Map<String, Customer> customersMap = new HashMap<>();
        customersMap.put(TestConstants.VALID_CUSTOMER_ID, Customer.builder().customerFirstName("John").customerLastName("Doe").customerPhoneNumber(TestConstants.VALID_CUSTOMER_ID).build());
        return customersMap;
    }

    // Utility method to create mock products map
    private static Map<Long, Product> getMockProductsMap() {
        Map<Long, Product> productsMap = new HashMap<>();
        productsMap.put(TestConstants.EXISTING_PRODUCT_ID, Product.builder().productId(TestConstants.EXISTING_PRODUCT_ID).productName("Product 1").productPrice(TestConstants.PRODUCT_PRICE).stock(TestConstants.AVAILABLE_STOCK).build());
        return productsMap;
    }

}
