package com.licious.ordermanagementsystem.utils;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderOperation;

public class OrderTask {
    private final Order order;
    private final OrderOperation operation;

    public OrderTask(Order order, OrderOperation operation) {
        this.order = order;
        this.operation = operation;
    }

    public Order getOrder() {
        return order;
    }

    public OrderOperation getOperation() {
        return operation;
    }
}

