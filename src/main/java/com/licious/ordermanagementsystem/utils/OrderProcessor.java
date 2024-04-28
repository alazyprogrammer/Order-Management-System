package com.licious.ordermanagementsystem.utils;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderOperation;
import com.licious.ordermanagementsystem.repository.OrderRepository;

public class OrderProcessor {

    private static final Logger logger = LogManager.getLogger(OrderProcessor.class);

    @Autowired
    private OrderRepository orderRepository;

    private ConcurrentLinkedQueue<OrderTask> orderQueue;
    private ExecutorService executorService;
    private int numThreads;

    public OrderProcessor(int numThreads) {
        this.numThreads = numThreads;
        executorService = Executors.newFixedThreadPool(numThreads);
        this.orderQueue = OrderQueueManager.getOrderQueue();
        startProcessing();
    }

    private void startProcessing() {
        for (int i = 0; i < numThreads; i++) {
            executorService.execute(new OrderWorker());
        }
    }

    public void enqueueOrder(Order order, OrderOperation operation) {
        logger.info("Adding order with ID: {} to the order queue", order.getOrderId());
        orderQueue.offer(new OrderTask(order, operation));
    }

    private class OrderWorker implements Runnable {
        @Override
        public void run() {
            while (true) {
                OrderTask orderTask = OrderQueueManager.getOrderQueue().poll();
                if (orderTask != null) {
                    logger.info("Processing order with ID: {} and task: {}", orderTask.getOrder().getOrderId(), orderTask.getOperation());
                    processOrderTask(orderTask);
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

        private void processOrderTask(OrderTask orderTask) {
            logger.info("Processing order: {}", orderTask.getOrder().getOrderId());
            Order order = orderTask.getOrder();
            OrderOperation operation = orderTask.getOperation();

            switch (operation) {
                case CREATE:
                    // Handle create order operation
                    logger.info("Creating order for ID: {}", order.getOrderId());
                    orderRepository.create(order);
                    break;
                case UPDATE:
                    // Handle update order operation
                    logger.info("Updating order: " + order);
                    break;
                case DELETE:
                    // Handle delete order operation
                    logger.info("Deleting order: " + order);
                    break;
                default:
                logger.info("Unsupported operation: " + operation);
            }
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
