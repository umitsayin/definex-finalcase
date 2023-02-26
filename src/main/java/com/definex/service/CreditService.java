package com.definex.service;

import com.definex.api.request.CreditInquiryRequest;
import com.definex.api.request.CustomerCreditRequest;
import com.definex.api.response.DataResponse;
import com.definex.dto.CreditDto;

import java.util.List;

public interface CreditService {
    DataResponse<CreditDto> getCreditFinalResult(CustomerCreditRequest request);
    DataResponse<List<CreditDto>> getCreditByCustomerIdentityNumberAndCustomerDateOfBirth(CreditInquiryRequest request);
}
