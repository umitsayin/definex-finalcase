package com.definex.service;

import com.definex.api.request.CreditInquiryRequest;
import com.definex.api.request.CustomerCreditRequest;
import com.definex.api.response.DataResponse;
import com.definex.constant.GlobalConstant;
import com.definex.dto.AuthorizedPersonDto;
import com.definex.dto.CreditDto;
import com.definex.dto.CustomerDto;
import com.definex.model.Credit;
import com.definex.model.Customer;
import com.definex.model.User;
import com.definex.repository.CreditRepository;
import com.definex.service.impl.CreditServiceImpl;
import com.definex.service.impl.CustomerServiceImpl;
import com.definex.util.helper.SystemHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreditServiceImplTest {
    private ModelMapper modelMapper;
    private SystemHelper systemHelper;
    private CustomerService customerService;
    private CreditRepository creditRepository;
    private CreditService creditService;
    private SmsService smsService;

    @BeforeEach
    public void setUp(){
        modelMapper = mock(ModelMapper.class);
        systemHelper = mock(SystemHelper.class);
        customerService = mock(CustomerServiceImpl.class);
        creditRepository = mock(CreditRepository.class);
        smsService = mock(SmsService.class);
        creditService = new CreditServiceImpl(modelMapper, systemHelper, customerService, creditRepository, smsService);
    }

    @Test
    void testGetCreditFinalResult_WithCustomerCreditRequest_thenReturnCreditDto(){
        UUID customerId =UUID.randomUUID();
        CustomerCreditRequest request = new CustomerCreditRequest();
        request.setCustomerId(String.valueOf(customerId));

        Customer customer = new Customer();
        customer.setIdentityNumber("1111111");
        customer.setFirstname("Ümit");
        customer.setLastname("Sayın");
        customer.setCreditScore(550);
        customer.setCollaterals(new ArrayList<>());
        customer.setSalary(7000);

        User user = new User();
        user.setFirstname("test firstname");
        user.setLastname("test lastname");
        user.setEmail("test@gmail.com");

        double totalCollateralPrice = 0d;

        Credit credit = new Credit();
        credit.setCurrency("TL");
        credit.setCustomerRepresentative(user);
        credit.setCreditAmount(GlobalConstant.MEDIUM_QUANTITY_LOAN_AMOUNT
                + totalCollateralPrice * 0.20);

        AuthorizedPersonDto authorizedPersonDto = new AuthorizedPersonDto();
        authorizedPersonDto.setFirstname("test firstname");
        authorizedPersonDto.setLastname("test lastname");
        authorizedPersonDto.setEmail("test@gmail.com");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber("1111111");
        customerDto.setFirstname("Ümit");
        customerDto.setLastname("Sayın");
        customerDto.setCreditScore(550);
        customerDto.setCollaterals(new ArrayList<>());
        customerDto.setSalary(7000);

        CreditDto creditDto = new CreditDto();
        creditDto.setCurrency("TL");
        creditDto.setCreditAmount(GlobalConstant.MEDIUM_QUANTITY_LOAN_AMOUNT
                + totalCollateralPrice * 0.20);
        creditDto.setCustomerRepresentative(authorizedPersonDto);
        creditDto.setCustomer(customerDto);

        when(customerService.getCustomerById(customerId)).thenReturn(customer);
        when(systemHelper.getCurrentUser()).thenReturn(user);
        when(modelMapper.map(user, AuthorizedPersonDto.class)).thenReturn(authorizedPersonDto);
        when(modelMapper.map(customer, CustomerDto.class)).thenReturn(customerDto);
        when(modelMapper.map(credit, CreditDto.class)).thenReturn(creditDto);

        DataResponse<CreditDto> result = creditService.getCreditFinalResult(request);

        assertEquals(result.getData(), creditDto);
    }

    @Test
    void testgetCreditByCustomerIdentityNumberAndCustomerDateOfBirth_WithCreditInquiryRequest_thenReturnCreditListDto(){
        Customer customer = new Customer();
        customer.setIdentityNumber("1111111");
        customer.setFirstname("Ümit");
        customer.setLastname("Sayın");
        customer.setCreditScore(550);
        customer.setCollaterals(new ArrayList<>());
        customer.setSalary(7000);

        User user = new User();
        user.setFirstname("test firstname");
        user.setLastname("test lastname");
        user.setEmail("test@gmail.com");

        AuthorizedPersonDto authorizedPersonDto = new AuthorizedPersonDto();
        authorizedPersonDto.setFirstname("test firstname");
        authorizedPersonDto.setLastname("test lastname");
        authorizedPersonDto.setEmail("test@gmail.com");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber("1111111");
        customerDto.setFirstname("Ümit");
        customerDto.setLastname("Sayın");
        customerDto.setCreditScore(550);
        customerDto.setCollaterals(new ArrayList<>());
        customerDto.setSalary(7000);

        double totalCollateralPrice = 0d;

        Credit credit = new Credit();
        credit.setCurrency("TL");
        credit.setCustomerRepresentative(user);
        credit.setCreditAmount(GlobalConstant.MEDIUM_QUANTITY_LOAN_AMOUNT
                + totalCollateralPrice * 0.20);
        credit.setCustomer(customer);
        credit.setCustomerRepresentative(user);

        List<Credit> creditList = new ArrayList<>();
        creditList.add(credit);

        CreditInquiryRequest request = new CreditInquiryRequest();
        request.setIdentityNumber("1111111");
        request.setDateOfBirth(null);

        List<CreditDto> creditDtoList = new ArrayList<>();
        CreditDto creditDto = new CreditDto();
        creditDto.setCurrency("TL");
        creditDto.setCreditAmount(GlobalConstant.MEDIUM_QUANTITY_LOAN_AMOUNT
                + totalCollateralPrice * 0.20);
        creditDto.setCustomerRepresentative(authorizedPersonDto);
        creditDto.setCustomer(customerDto);

        creditDtoList.add(creditDto);

        when(creditRepository.findCreditByCustomerIdentityNumberAndCustomerDateOfBirth
                (request.getIdentityNumber(),request.getDateOfBirth()))
                .thenReturn(Optional.of(creditList));
        when(modelMapper.map(credit, CreditDto.class)).thenReturn(creditDto);



        final DataResponse<List<CreditDto>> result =
                creditService.getCreditByCustomerIdentityNumberAndCustomerDateOfBirth(request);

        assertEquals(creditDtoList, result.getData());
    }
}