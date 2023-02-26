package com.definex.api.request;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomerPostRequest {
    private String identityNumber;
    private String firstname;
    private String lastname;
    private String phone;
    private double salary;
    private Date dateOfBirth;
    private List<CustomerCollateralRequest> collaterals;
}
