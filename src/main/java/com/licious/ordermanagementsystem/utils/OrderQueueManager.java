package com.licious.ordermanagementsystem.utils;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderOperation;

public class OrderQueueManager {
    private static final ConcurrentLinkedQueue<OrderTask> orderQueue = new ConcurrentLinkedQueue<>();

    private OrderQueueManager() {} // private constructor to prevent instantiation

    public static ConcurrentLinkedQueue<OrderTask> getOrderQueue() {
        return orderQueue;
    }

    public static void enqueueOrder(Order order, OrderOperation operation) {
        orderQueue.offer(new OrderTask(order, operation));
    }
}

