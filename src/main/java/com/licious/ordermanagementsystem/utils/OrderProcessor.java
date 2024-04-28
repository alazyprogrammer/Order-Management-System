package com.licious.ordermanagementsystem.utils;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.OrderOperation;
import com.licious.ordermanagementsystem.repository.OrderRepository;

public class OrderProcessor {

    private static final Logger logger = LogManager.getLogger(OrderProcessor.class);

    private ConcurrentLinkedQueue<OrderTask> orderQueue = new ConcurrentLinkedQueue<>();
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

    public void enqueueOrder(Order order, OrderOperation operation) {
        orderQueue.offer(new OrderTask(order, operation));
    }

    private class OrderWorker implements Runnable {
        @Override
        public void run() {
            while (true) {
                OrderTask orderTask = OrderQueueManager.getOrderQueue().poll();
                if (orderTask != null) {
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
            logger.info("Processing order: " + orderTask.getOrder());
            Order order = orderTask.getOrder();
            OrderOperation operation = orderTask.getOperation();

            switch (operation) {
                case CREATE:
                    // Handle create order operation
                    logger.info("Creating order: " + order);
                    orderRepository.save(order);
                    break;
                case RETRIEVE:
                    // Handle update order operation
                    logger.info("Retreving details for order with ID: " + order);
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
