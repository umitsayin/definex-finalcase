package com.definex.dto;

import com.definex.model.Collateral;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomerDto extends BaseDto{
    private String identityNumber;
    private String firstname;
    private String lastname;
    private String phone;
    private double salary;
    private int creditScore;
    private Date dateOfBirth;
    private List<Collateral> collaterals;
    private UserDto user;
}
