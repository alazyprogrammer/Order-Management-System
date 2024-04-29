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

### Cancel Order
- **Endpoint**: `DELETE /orders/{orderId}/cancel`
- **Response**: Returns a success message with HTTP status code `200 OK` if the order is canceled successfully.

### Retrieve Order Details
- **Endpoint**: `GET /orders/{orderId}`
- **Response**: Returns the order details with HTTP status code `200 OK` if the order exists, or HTTP status code `404 Not Found` if the order does not exist.

## Concurrency Model
The application follows a multithreaded architecture to handle multiple requests concurrently. Spring Boot provides built-in support for handling concurrency with thread-safe components such as controllers and services. Additionally, synchronization mechanisms like locks or atomic operations are used to ensure thread safety when accessing shared resources, such as the order database.

## Contributors
- Sakir Beg

---
