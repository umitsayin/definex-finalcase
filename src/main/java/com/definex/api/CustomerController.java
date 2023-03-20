package com.definex.api;

import com.definex.api.request.CustomerPostRequest;
import com.definex.api.request.CustomerPutRequest;
import com.definex.api.response.BaseResponse;
import com.definex.api.response.DataResponse;
import com.definex.dto.CustomerDto;
import com.definex.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<CustomerDto>>> getAll(){
        return ResponseEntity.ok(customerService.getAll());
    }
    @PostMapping
    public ResponseEntity<DataResponse<CustomerDto>> createCustomer(@RequestBody CustomerPostRequest customerPostRequest){
        return ResponseEntity.ok(customerService.createCustomer(customerPostRequest));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<DataResponse<CustomerDto>> updateCustomerById(@PathVariable String customerId,
                                                @RequestBody CustomerPutRequest customerPutRequest){
        return ResponseEntity.ok(customerService.updateCustomerById(customerId, customerPutRequest));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<BaseResponse> deleteCustomerById(@PathVariable String customerId){
        return ResponseEntity.ok(customerService.deleteCustomerById(customerId));
    }
}
