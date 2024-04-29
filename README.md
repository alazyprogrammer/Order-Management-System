# Order Management System

## Introduction
This is an Order Management System application designed to manage orders for a business. It provides APIs for creating, updating, canceling, and retrieving orders.

## Setup
1. **Clone the repository**: `git clone <repository-url>`
2. **Navigate to the project directory**: `cd order-management-system`
3. **Build the project**: `./gradlew build`
4. **Run the application**: `./gradlew bootRun`
5. **Access the application**: Open a web browser and go to `http://localhost:8080`

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
  Order with ID order123 has been cancelled successfully.
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

## Concurrency Model
The application follows a multithreaded architecture to handle multiple requests concurrently. Spring Boot provides built-in support for handling concurrency with thread-safe components such as controllers and services. Additionally, synchronization mechanisms like locks or atomic operations are used to ensure thread safety when accessing shared resources, such as the order database.

## Contributors
- Sakir Beg

---
