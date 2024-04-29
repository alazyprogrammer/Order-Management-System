package com.licious.ordermanagementsystem.model.api;

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
public class OrderRequest {

    @NonNull
    private String customerId;
    private Map<Long, Integer> products;
    private Double shippingCharge;
    private Double subTotal;
    private LocalDateTime orderDate;
    private boolean paymentStatus;

}
