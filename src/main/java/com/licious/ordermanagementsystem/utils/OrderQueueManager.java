package com.licious.ordermanagementsystem.utils;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.licious.ordermanagementsystem.model.Order;

public class OrderQueueManager {
    private static final ConcurrentLinkedQueue<Order> orderQueue = new ConcurrentLinkedQueue<>();

    private OrderQueueManager() {} // private constructor to prevent instantiation

    public static ConcurrentLinkedQueue<Order> getOrderQueue() {
        return orderQueue;
    }

    public static void enqueueOrder(Order order) {
        orderQueue.offer(order);
    }
}

