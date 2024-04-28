package com.licious.ordermanagementsystem.model;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @NonNull
    private Long productId;
    private String productName;
    private String productDescription;
    private double productPrice;

}

