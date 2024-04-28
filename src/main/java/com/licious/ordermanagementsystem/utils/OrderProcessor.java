package com.licious.ordermanagementsystem.utils;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.repository.OrderRepository;

public class OrderProcessor {
    private ConcurrentLinkedQueue<Order> orderQueue = new ConcurrentLinkedQueue<>();
    private OrderRepository orderRepository;
    private ExecutorService executorService;
    private int numThreads;

    public OrderProcessor(int numThreads) {
        this.numThreads = numThreads;
        executorService = Executors.newFixedThreadPool(numThreads);
        startProcessing();
    }

    private void startProcessing() {
        for (int i = 0; i < numThreads; i++) {
            executorService.execute(new OrderWorker());
        }
    }

    public void enqueueOrder(Order order) {
        orderQueue.offer(order);
    }

    private class OrderWorker implements Runnable {
        @Override
        public void run() {
            while (true) {
                Order order = OrderQueueManager.getOrderQueue().poll();
                if (order != null) {
                    processOrder(order);
                } else {
                    // Queue is empty, wait or perform other actions
                    // For simplicity, sleep for a short duration
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        private void processOrder(Order order) {
            // Add your order processing logic here
            System.out.println("\nProcessing order: " + order.getOrderId());
            orderRepository.save(order);
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
