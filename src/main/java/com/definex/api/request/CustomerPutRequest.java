package com.definex.api.request;

import lombok.Data;

import java.util.List;

@Data
public class CustomerPutRequest {
    private String phone;
    private int salary;
    private List<CustomerCollateralRequest> collaterals;
}
