package com.definex.service.impl;

import com.definex.api.request.CreditInquiryRequest;
import com.definex.api.request.CustomerCreditRequest;
import com.definex.api.response.DataResponse;
import com.definex.api.response.SuccessDataResponse;
import com.definex.constant.GlobalConstant;
import com.definex.dto.CreditDto;
import com.definex.dto.CustomerDto;
import com.definex.exception.EntityNotFoundException;
import com.definex.dto.AuthorizedPersonDto;
import com.definex.model.Credit;
import com.definex.model.Customer;
import com.definex.repository.CreditRepository;
import com.definex.service.CreditService;
import com.definex.service.CustomerService;
import com.definex.service.SmsService;
import com.definex.util.helper.CreditHelper;
import com.definex.util.helper.SystemHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CreditServiceImpl implements CreditService {
    private final ModelMapper modelMapper;
    private final SystemHelper systemHelper;
    private final CustomerService customerService;
    private final CreditRepository creditRepository;
    private final SmsService smsService;

    public CreditServiceImpl(ModelMapper modelMapper, SystemHelper systemHelper, CustomerService customerService, CreditRepository creditRepository, SmsService smsService) {
        this.modelMapper = modelMapper;
        this.systemHelper = systemHelper;
        this.customerService = customerService;
        this.creditRepository = creditRepository;
        this.smsService = smsService;
    }

    @Override
    public DataResponse<CreditDto> getCreditFinalResult(CustomerCreditRequest request) {
        final Customer customer = customerService.getCustomerById(UUID.fromString(request.getCustomerId()));
        final Credit credit = CreditHelper.calculateCreditByCustomer(customer, customerService, systemHelper);

        customer.getCredits().add(creditRepository.save(credit));
        customerService.saveCustomerCredit(customer);

        final AuthorizedPersonDto authorizedPerson = modelMapper.map(systemHelper.getCurrentUser(),AuthorizedPersonDto.class);
        final CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        final CreditDto creditDto = modelMapper.map(credit, CreditDto.class);

        creditDto.setCustomerRepresentative(authorizedPerson);
        creditDto.setCustomer(customerDto);

        log.info("Credit creation service called");
        smsService.sendSms(customer.getPhone(), GlobalConstant.SMS_SEND_MESSAGE);

        return new SuccessDataResponse<CreditDto>(GlobalConstant.CREDIT_APPROVED, creditDto);
    }

    @Override
    public DataResponse<List<CreditDto>> getCreditByCustomerIdentityNumberAndCustomerDateOfBirth(CreditInquiryRequest request) {
        final List<Credit> credits =
                creditRepository.findCreditByCustomerIdentityNumberAndCustomerDateOfBirth(request.getIdentityNumber(), request.getDateOfBirth())
                        .orElseThrow(()-> new EntityNotFoundException(GlobalConstant.CREDIT_NOT_FOUND));

        final List<CreditDto> creditDtoList =  credits.stream()
                .map(item -> modelMapper.map(item, CreditDto.class)).collect(Collectors.toList());

        log.info("Credit viewed with ID number and date of birth");

        return new SuccessDataResponse<List<CreditDto>>(GlobalConstant.CREDIT_LISTED, creditDtoList);
    }
}
