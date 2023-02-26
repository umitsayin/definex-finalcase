package com.definex.api.request;

import lombok.Data;

@Data
public class CustomerCollateralRequest {
    private String collateral;
    private double price;
    private String currency;
}
