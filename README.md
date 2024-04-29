# Order Management System

## Introduction

The Order Management System is a comprehensive application designed to streamline the process of managing orders for businesses, providing a robust platform for creating, updating, canceling, and retrieving orders. Built with modern technologies and best practices, the system offers a scalable and efficient solution to meet the evolving needs of businesses in the e-commerce and retail sectors.

### Technologies Used

#### Spring Boot
- **Purpose**: Spring Boot provides the foundation for building robust and scalable Java applications with minimal configuration. Its convention-over-configuration approach simplifies development and accelerates time-to-market.
- **Key Features**: Dependency injection, auto-configuration, and embedded application servers make Spring Boot ideal for developing microservices and web applications.

#### Gradle
- **Purpose**: Gradle is a powerful build automation tool used to manage project dependencies, compile source code, and package applications.
- **Key Features**: Gradle offers a flexible and declarative build configuration syntax, allowing developers to define custom tasks and automate repetitive build processes efficiently.

#### Java
- **Purpose**: Java is a versatile programming language known for its platform independence, strong typing, and object-oriented programming paradigm.
- **Key Features**: Java's rich ecosystem of libraries, frameworks, and tools make it well-suited for developing enterprise-grade applications with high performance, reliability, and scalability.

### Purpose of the Order Management System
The Order Management System serves as a central hub for businesses to manage their order-related operations efficiently and effectively. By leveraging modern technologies and best practices, the system offers the following benefits:

1. **Streamlined Order Processing**: The system simplifies the process of creating, updating, and canceling orders, enabling businesses to fulfill customer requests promptly and accurately.

2. **Improved Efficiency**: With automated order processing and streamlined workflows, businesses can optimize resource allocation and reduce manual intervention, leading to increased operational efficiency.

3. **Enhanced Customer Experience**: By ensuring timely order fulfillment and accurate order tracking, the system enhances the overall customer experience, fostering customer satisfaction and loyalty.

4. **Data Integrity and Security**: Robust validation mechanisms and authentication/authorization protocols ensure the integrity and security of order data, safeguarding sensitive information and mitigating security risks.

5. **Scalability and Flexibility**: Built on scalable and flexible architecture, the system can adapt to the growing needs of businesses, accommodating changes in order volumes, product offerings, and business requirements seamlessly.

### Key Components
The Order Management System comprises several key components, including:

- **API Layer**: Exposes RESTful APIs for creating, updating, canceling, and retrieving orders.
- **Service Layer**: Implements business logic and orchestrates interactions between the API layer and data access layer.
- **Data Access Layer**: Manages data persistence and interacts with the underlying database or data storage system.
- **Validation Utilities**: Implements comprehensive validation mechanisms to ensure the integrity and validity of incoming requests and data.

---

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

## API Validations and Security

### Validations
The Order Management System incorporates comprehensive validations to ensure the integrity and consistency of data processed by its APIs. Each API endpoint performs specific validations to validate incoming requests and ensure that only valid data is processed. Here's a breakdown of the validations performed for each API:

1. **Create Order API** (`POST /orders/create`):
   - Validate `customerId` is not null or empty.
   - Validate `products` map is not null or empty.
   - Validate each product ID and quantity.
   - Check if the customer exists.
   - Additional business rule validations (e.g., product stock availability).

2. **Update Order Status API** (`PUT /orders/status`):
   - Validate `orderId`, `customerId`, and `status`.
   - Validate order status against predefined values.
   - Validate order existence.
   - Validate user existence.
   - Validate status transition based on the current order status.

3. **Cancel Order API** (`DELETE /orders/{orderId}/cancel`):
   - Validate `orderId`.
   - Check if order exists.
   - Validate order status for cancellation.

4. **Retrieve Order Details API** (`GET /orders/{orderId}`):
   - Validate `orderId`.
   - Check if the order exists.

### Authentication and Authorization
While the Order Management System focuses on data validation and integrity, enhancing security is paramount for protecting sensitive information and preventing unauthorized access. Implementing authentication and authorization mechanisms can further enhance the security of the APIs. Here are some approaches to consider:

- **Authentication**: Implement user authentication to verify the identity of users accessing the system. This can be achieved using techniques such as JSON Web Tokens (JWT), OAuth, or basic authentication.
  
- **Authorization**: Enforce access control policies to determine which users have permission to access specific APIs or perform certain actions. Role-based access control (RBAC) or attribute-based access control (ABAC) can be employed to manage authorization rules effectively.

- **Secure Communication**: Ensure that communication between clients and the server is encrypted using HTTPS to prevent eavesdropping and man-in-the-middle attacks.

By implementing robust authentication and authorization mechanisms, the Order Management System can protect sensitive data, mitigate security risks, and comply with regulatory requirements, thereby enhancing overall system security.

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
