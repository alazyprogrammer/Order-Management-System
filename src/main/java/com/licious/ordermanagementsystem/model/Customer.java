package com.licious.ordermanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    private String customerFirstName;
    private String customerLastName;
    private String customerEmail; // Unique Identifier
    private String customerPhoneNumber;
    private String customerAddress;
    private String customerCity;
    private String customerState;
    private String customerPincode;

}
