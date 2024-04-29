package com.licious.ordermanagementsystem.Mapper;

import org.modelmapper.ModelMapper;

import com.licious.ordermanagementsystem.model.Order;
import com.licious.ordermanagementsystem.model.api.OrderRequest;

public class OrderMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Order mapOrderRequestToOrder(OrderRequest orderRequest) {
        Order order = modelMapper.map(orderRequest, Order.class);
        return order;
    }
    
}
