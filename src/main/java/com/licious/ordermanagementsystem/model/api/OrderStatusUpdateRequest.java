package com.licious.ordermanagementsystem.model.api;

import com.licious.ordermanagementsystem.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusUpdateRequest {

    @NonNull
    private String customerId;
    @NonNull
    private String orderId;
    @NonNull
    private OrderStatus status;

}
