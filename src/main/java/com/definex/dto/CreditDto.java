package com.definex.dto;

import lombok.Data;

@Data
public class CreditDto extends BaseDto {
    private double creditAmount;
    private String currency;
    private CustomerDto customer;
    private AuthorizedPersonDto customerRepresentative;
}
