package com.definex.api;

import com.definex.api.request.CreditInquiryRequest;
import com.definex.api.request.CustomerCreditRequest;
import com.definex.api.response.DataResponse;
import com.definex.dto.CreditDto;
import com.definex.service.CreditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/credit")
public class CreditController {
    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping
    public ResponseEntity<DataResponse<CreditDto>> getCreditFinalResult(@RequestBody CustomerCreditRequest request){
        return ResponseEntity.ok(creditService.getCreditFinalResult(request));
    }

    @GetMapping("/list")
    public ResponseEntity<DataResponse<List<CreditDto>>> getCreditByCustomerIdentityNumberAndCustomerDateOfBirth(@RequestBody CreditInquiryRequest request){
        return ResponseEntity.ok(creditService.getCreditByCustomerIdentityNumberAndCustomerDateOfBirth(request));
    }
}
