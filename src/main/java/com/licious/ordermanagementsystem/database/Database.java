package com.licious.ordermanagementsystem.database;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.Customer;
import com.licious.ordermanagementsystem.model.Product;

import java.util.Map;

@Component
public class Database {
    private final Map<String, Order> ordersMap = new ConcurrentHashMap<>();
    private final Map<String, Customer> customersMap = new ConcurrentHashMap<>();
    private final Map<Long, Product> productsMap = new ConcurrentHashMap<>();

    public Database() {
        // Initialize customers
        initializeCustomers();

        // Initialize products
        initializeProducts();
    }

    private void initializeCustomers() {
        // Add 5 sample customers
        customersMap.put("1234567890", new Customer("John", "Doe", "john@example.com", "1234567890", "123 Main St", "Anytown", "CA", "12345"));
        customersMap.put("9876543210", new Customer("Alice", "Smith", "alice@example.com", "9876543210", "456 Oak St", "Othertown", "NY", "54321"));
        customersMap.put("1112223333", new Customer("Bob", "Johnson", "bob@example.com", "1112223333", "789 Elm St", "Somewhere", "TX", "67890"));
        customersMap.put("4445556666", new Customer("Emily", "Brown", "emily@example.com", "4445556666", "321 Pine St", "Nowhere", "FL", "98765"));
        customersMap.put("7778889999", new Customer("Michael", "Wilson", "michael@example.com", "7778889999", "654 Cedar St", "Everywhere", "WA", "45678"));
    }

    private void initializeProducts() {
        // Add 5 sample products
        productsMap.put(1L, new Product(1L, "Product A", "Description for Product A", 10.00, 5));
        productsMap.put(2L, new Product(2L, "Product B", "Description for Product B", 20.00, 3));
        productsMap.put(3L, new Product(3L, "Product C", "Description for Product C", 30.00, 6));
        productsMap.put(4L, new Product(4L, "Product D", "Description for Product D", 40.00, 10));
        productsMap.put(5L, new Product(5L, "Product E", "Description for Product E", 50.00, 1));
    }

    // Methods to access and manipulate the maps

    public Map<String, Order> getOrdersMap() {
        return ordersMap;
    }

    public Map<String, Customer> getCustomersMap() {
        return customersMap;
    }

    public Map<Long, Product> getProductsMap() {
        return productsMap;
    }
    
}

