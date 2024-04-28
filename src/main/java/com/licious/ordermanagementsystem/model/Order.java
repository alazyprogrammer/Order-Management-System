package com.licious.ordermanagementsystem.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @NonNull
    private String customerId;
    private String orderId;
    private Map<Long, Integer> products;
    private Double shippingCharge;
    private Double subTotal;
    private LocalDateTime orderDate;
    private boolean paymentStatus;
    private OrderStatus orderStatus;

}

