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
import com.licious.ordermanagementsystem.model.Product;
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
    public void testCreateOrder_WithValidData_ReturnsCreated() {
        // Mock database
        when(database.getCustomersMap()).thenReturn(getMockCustomersMap());
        when(database.getProductsMap()).thenReturn(getMockProductsMap());

        // Mock order service
        when(orderService.createOrder(TestConstants.VALID_ORDER_DETAILS)).thenReturn(new Order());

        // Invoke API
        ResponseEntity<?> responseEntity = orderController.createOrder(TestConstants.VALID_ORDER_DETAILS);

        // Verify response
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateOrder_WithInvalidCustomer_ReturnsBadRequest() {
        // Mock database
        when(database.getCustomersMap()).thenReturn(getMockCustomersMap());
        when(database.getProductsMap()).thenReturn(getMockProductsMap());

        // Invoke API with invalid customer ID
        TestConstants.VALID_ORDER_DETAILS.setCustomerId(TestConstants.NON_EXISTING_CUSTOMER_ID);
        ResponseEntity<?> responseEntity = orderController.createOrder(TestConstants.VALID_ORDER_DETAILS);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateOrder_WithMissingCustomerId_ReturnsBadRequest() {
        // Prepare test data with missing customer ID
        Order orderDetails = new Order();
        // Set products to make the order valid
        Map<Long, Integer> products = new HashMap<>();
        products.put(1L, 2);
        orderDetails.setProducts(products);

        // Invoke controller method
        ResponseEntity<?> response = orderController.createOrder(orderDetails);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Customer ID is required", response.getBody());
    }

    @Test
    public void testCreateOrder_WithMissingProducts_ReturnsBadRequest() {
        // Prepare test data with missing products
        Order orderDetails = new Order();
        // Set customer ID to make the order valid
        orderDetails.setCustomerId(TestConstants.VALID_CUSTOMER_ID);

        // Invoke controller method
        ResponseEntity<?> response = orderController.createOrder(orderDetails);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Products are is required", response.getBody());
    }

    @Test
    public void testCreateOrder_WithInvalidProductIdOrQuantity_ReturnsBadRequest() {
        // Prepare test data with invalid product ID or quantity
        Order orderDetails = new Order();
        orderDetails.setCustomerId(TestConstants.VALID_CUSTOMER_ID);
        // Add an invalid product ID or quantity
        Map<Long, Integer> products = new HashMap<>();
        products.put(null, 2); // Invalid product ID
        products.put(1L, -1); // Invalid quantity
        orderDetails.setProducts(products);

        // Invoke controller method
        ResponseEntity<?> response = orderController.createOrder(orderDetails);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid product ID or quantity", response.getBody());
    }

    @Test
    public void testCreateOrder_WithNonexistentProduct_ReturnsBadRequest() {
        // Prepare test data with nonexistent product
        Order orderDetails = new Order();
        orderDetails.setCustomerId(TestConstants.VALID_CUSTOMER_ID);
        // Add a nonexistent product ID
        Map<Long, Integer> products = new HashMap<>();
        products.put(100L, 2); // Nonexistent product ID
        orderDetails.setProducts(products);

        when(database.getProductsMap()).thenReturn(getMockProductsMap());

        // Invoke controller method
        ResponseEntity<?> response = orderController.createOrder(orderDetails);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Product with ID 100 does not exist", response.getBody());
    }


    @Test
    public void testCreateOrder_WithInsufficientStock_ReturnsBadRequest() {
        // Prepare test data with insufficient stock
        Order orderDetails = new Order();
        orderDetails.setCustomerId(TestConstants.VALID_CUSTOMER_ID);
        // Add products with insufficient stock
        Map<Long, Integer> products = new HashMap<>();
        products.put(1L, 10); // Product ID 1 has insufficient stock (5 in stock)
        orderDetails.setProducts(products);

        when(database.getProductsMap()).thenReturn(getMockProductsMap());

        // Invoke controller method
        ResponseEntity<?> response = orderController.createOrder(orderDetails);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Not enough stock for product with ID 1", response.getBody());
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
