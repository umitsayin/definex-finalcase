package com.definex.api.request;

import lombok.Data;

import java.util.Date;

@Data
public class CreditInquiryRequest {
    private String identityNumber;
    private Date dateOfBirth;
}
