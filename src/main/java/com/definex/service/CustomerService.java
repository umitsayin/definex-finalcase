package com.definex.service;

import com.definex.api.request.CustomerPostRequest;
import com.definex.api.request.CustomerPutRequest;
import com.definex.api.response.BaseResponse;
import com.definex.api.response.DataResponse;
import com.definex.dto.CustomerDto;
import com.definex.model.Collateral;
import com.definex.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    DataResponse<CustomerDto> createCustomer(CustomerPostRequest customerPostRequest);
    DataResponse<CustomerDto> updateCustomerById(String customerId, CustomerPutRequest customerPutRequest);
    BaseResponse deleteCustomerById(String customerId);
    Customer getCustomerById(UUID id);
    double totalCollateralPrice(List<Collateral> collateralList);
    void saveCustomerCredit(Customer customer);
}
