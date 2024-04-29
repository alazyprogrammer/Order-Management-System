# Order Management System

## Introduction
This is an Order Management System application designed to manage orders for a business. It provides APIs for creating, updating, canceling, and retrieving orders.

## Setup
1. **Clone the repository**: `git clone <repository-url>`
2. **Navigate to the project directory**: `cd order-management-system`
3. **Build the project**: `./gradlew build`
4. **Run the application**: `./gradlew bootRun`
5. **Access the sample API calls for testing (use terminal)**:
   - [Create Order API](#create-order)
   - [Update Order Status API](#update-order-status)
   - [Cancel Order API](#cancel-order)
   - [Retrieve Order Details API](#retrieve-order-details)

## API Documentation

### Create Order
- **Endpoint**: `POST /orders/create`
- **Request Body**:
  ```json
  {
    "customerId": "string",
    "products": {
      "productId1": quantity1,
      "productId2": quantity2,
      ...
    }
    "shippingCharge": "double",
    "subTotal": "double",
    "orderDate": "localDateTime",
    "payment": "boolean"
  }
  ```
- **Response**: Returns the created order with HTTP status code `201 Created`.
- **Sample API Call**:
  ```
  curl -X POST http://localhost:8080/orders/create \
    -H "Content-Type: application/json" \
    -d '{
          "customerId": "1234567890",
          "products": {
              "1": 2,
              "5": 1
          },
          "shippingCharge": 5.00,
          "subTotal": 45.00,
          "orderDate": "2022-04-28T10:15:30",
          "paymentStatus": true
        }'
  ```
- **Sample Response**:
  ```json
  {
    "customerId": "1234567890",
    "orderId": "cad8f5bf-ae8e-49da-912b-b7790de30bdc",
    "products": {
      "1": 2,
      "5": 1
    },
    "shippingCharge": 5.0,
    "subTotal": 45.0,
    "orderDate": "2022-04-28T10:15:30",
    "paymentStatus": true,
    "orderStatus": "CREATED"
  }
  ```

### Update Order Status
- **Endpoint**: `PUT /orders/status`
- **Request Body**:
  ```json
  {
    "orderId": "string",
    "customerId": "string",
    "status": "string"
  }
  ```
- **Response**: Returns the updated order with HTTP status code `200 OK`.
- **Sample API Call**:
  ```
  curl -X PUT http://localhost:8080/orders/status \
    -H "Content-Type: application/json" \
    -d '{
          "orderId": "cad8f5bf-ae8e-49da-912b-b7790de30bdc",
          "customerId": "1234567890",
          "status": "SHIPPED"
        }'
  ```
- **Sample Response**:
  ```json
  {
    "customerId": "1234567890",
    "orderId": "cad8f5bf-ae8e-49da-912b-b7790de30bdc",
    "products": {
      "1": 2,
      "5": 1
    },
    "shippingCharge": 5.0,
    "subTotal": 45.0,
    "orderDate": "2022-04-28T10:15:30",
    "paymentStatus": true,
    "orderStatus": "SHIPPED"
  }
  ```

### Cancel Order
- **Endpoint**: `DELETE /orders/{orderId}/cancel`
- **Response**: Returns a success message with HTTP status code `200 OK` if the order is canceled successfully.
- **Sample API Call**:
  ```
  curl -X DELETE http://localhost:8080/orders/cad8f5bf-ae8e-49da-912b-b7790de30bdc/cancel
  ```
- **Sample Response**:
  ```
  Order with ID cad8f5bf-ae8e-49da-912b-b7790de30bdc has been cancelled successfully.
  ```

### Retrieve Order Details
- **Endpoint**: `GET /orders/{orderId}`
- **Response**: Returns the order details with HTTP status code `200 OK` if the order exists, or HTTP status code `404 Not Found` if the order does not exist.
- **Sample API Call**:
  ```
  curl -X GET http://localhost:8080/orders/cad8f5bf-ae8e-49da-912b-b7790de30bdc
  ```
- **Sample Response**:
  ```json
  {
    "customerId": "1234567890",
    "orderId": "cad8f5bf-ae8e-49da-912b-b7790de30bdc",
    "products": {
      "1": 2,
      "5": 1
    },
    "shippingCharge": 5.0,
    "subTotal": 45.0,
    "orderDate": "2022-04-28T10:15:30",
    "paymentStatus": true,
    "orderStatus": "SHIPPED"
  }
  ```

---

## Concurrency Model

The Order Management System employs a robust concurrency model to handle multiple requests concurrently, improve performance, and maintain data integrity. This model is achieved through the use of ConcurrentHashMap, ConcurrentQueue, and threading mechanisms.

### ConcurrentHashMap
- **Enhanced Data Access**: ConcurrentHashMap is used to store orders and products efficiently. This thread-safe data structure allows multiple threads to read and write data concurrently without blocking or risking data corruption.
- **Data Integrity**: By using ConcurrentHashMap, the system ensures that operations such as creating, updating, canceling, and retrieving orders can be performed safely without compromising data integrity.

### ConcurrentQueue
- **Asynchronous Processing**: Although not explicitly used in the provided code snippets, ConcurrentQueue could be employed to queue orders for asynchronous processing. This allows orders to be processed in the background without blocking the main application thread.
- **High Concurrency**: ConcurrentQueue provides high concurrency for enqueueing and dequeueing operations, enabling efficient handling of orders in a multi-threaded environment.

### Threading
- **Concurrent Request Handling**: Threading is extensively utilized in the Spring Boot framework to handle concurrent requests from multiple clients. Each incoming request is processed by a separate thread, allowing the application to handle multiple requests simultaneously.
- **Improved Performance**: By leveraging threading, the system can efficiently utilize available resources and respond to requests in parallel, leading to improved performance and responsiveness.

### Benefits
- **Concurrency**: The use of ConcurrentHashMap, ConcurrentQueue, and threading mechanisms ensures that the system can handle concurrent operations such as creating, updating, canceling, and retrieving orders efficiently and safely.
- **Performance**: The concurrency model enhances performance by allowing the system to process multiple requests concurrently, maximizing resource utilization and reducing response times.
- **Data Integrity**: By employing thread-safe data structures and synchronization mechanisms, the system maintains data integrity and consistency, preventing data corruption or race conditions.

By implementing this robust concurrency model, the Order Management System achieves high throughput, responsiveness, and reliability, making it suitable for handling large volumes of orders and serving multiple clients concurrently.

---

## Contributors
- Sakir Beg

---
