package com.licious.ordermanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.licious.ordermanagementsystem.utils.OrderProcessor;

@SpringBootApplication
public class OrdermanagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdermanagementsystemApplication.class, args);
	}

	@Bean
    public OrderProcessor orderProcessor() {
        // Initialize OrderProcessor with the desired number of threads
        return new OrderProcessor(8);
    }

}
