package com.definex.service;

import com.definex.api.request.CustomerCollateralRequest;
import com.definex.api.request.CustomerPostRequest;
import com.definex.api.request.CustomerPutRequest;
import com.definex.api.response.DataResponse;
import com.definex.dto.CustomerDto;
import com.definex.dto.UserDto;
import com.definex.exception.EntityNotFoundException;
import com.definex.model.Collateral;
import com.definex.model.Customer;
import com.definex.model.User;
import com.definex.repository.CustomerRepository;
import com.definex.service.impl.CustomerServiceImpl;
import com.definex.util.CollateralType;
import com.definex.util.helper.SystemHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceImplTest {
    private ModelMapper modelMapper;
    private CustomerRepository customerRepository;
    private CollateralService collateralService;
    private SystemHelper systemHelper;
    private CustomerService customerService;

    @BeforeEach
    public void setUp(){
        modelMapper = mock(ModelMapper.class);
        customerRepository = mock(CustomerRepository.class);
        collateralService = mock(CollateralService.class);
        systemHelper = mock(SystemHelper.class);
        customerService = new CustomerServiceImpl(modelMapper, customerRepository, collateralService, systemHelper);
    }

    @Test
    void testCreateCustomer_WithCustomerPostRequest_thenReturnCustomerDto(){
        User user = new User();
        user.setFirstname("test firstname");
        user.setLastname("test lastname");
        user.setEmail("test@test.com");

        List<Collateral> collateralList = new ArrayList<>();
        Collateral collateral = new Collateral();

        collateral.setCollateral(CollateralType.CAR);
        collateral.setCurrency("TL");
        collateral.setPrice(400000);
        collateralList.add(collateral);

        CustomerCollateralRequest collateralRequest = new CustomerCollateralRequest();
        collateralRequest.setCollateral(String.valueOf(CollateralType.CAR));
        collateralRequest.setCurrency("TL");
        collateralRequest.setPrice(400000);

        List<CustomerCollateralRequest> customerCollateralRequests = new ArrayList<>();
        customerCollateralRequests.add(collateralRequest);

        CustomerPostRequest customerPostRequest = new CustomerPostRequest();
        customerPostRequest.setIdentityNumber("1111111");
        customerPostRequest.setFirstname("Ümit");
        customerPostRequest.setLastname("Sayın");
        customerPostRequest.setCollaterals(customerCollateralRequests);
        customerPostRequest.setSalary(7000);

        Customer customer = new Customer();
        customer.setIdentityNumber("1111111");
        customer.setFirstname("Ümit");
        customer.setLastname("Sayın");
        customer.setCreditScore(550);
        customer.setCollaterals(new ArrayList<>());
        customer.setSalary(7000);

        UserDto userDto = new UserDto();
        userDto.setFirstname("test firstname");
        userDto.setLastname("test lastname");
        userDto.setEmail("test@test.com");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber("1111111");
        customerDto.setFirstname("Ümit");
        customerDto.setLastname("Sayın");
        customerDto.setCreditScore(550);
        customerDto.setCollaterals(new ArrayList<>());
        customerDto.setSalary(7000);
        customerDto.setCollaterals(collateralList);
        customerDto.setUser(userDto);

        when(systemHelper.getCurrentUser()).thenReturn(user);
        when(collateralService.createCollateral(collateralRequest)).thenReturn(collateral);
        when(modelMapper.map(customerRepository.save(customer), CustomerDto.class)).thenReturn(customerDto);

        DataResponse<CustomerDto> result = customerService.createCustomer(customerPostRequest);

        assertEquals(customerDto, result.getData());
    }


    @Test
    void testUpdateCustomerById_withCustomerPutRequest_thenReturnCustomerDto(){
        UUID customerId = UUID.randomUUID();
        CustomerCollateralRequest collateralRequest = new CustomerCollateralRequest();
        collateralRequest.setCollateral(String.valueOf(CollateralType.CAR));
        collateralRequest.setCurrency("TL");
        collateralRequest.setPrice(400000);

        List<Collateral> collateralList = new ArrayList<>();
        Collateral collateral = new Collateral();

        collateral.setCollateral(CollateralType.CAR);
        collateral.setCurrency("TL");
        collateral.setPrice(400000);
        collateralList.add(collateral);

        List<CustomerCollateralRequest> customerPutRequests = new ArrayList<>();
        customerPutRequests.add(collateralRequest);
        CustomerPutRequest customerPutRequest = new CustomerPutRequest();
        customerPutRequest.setCollaterals(customerPutRequests);
        customerPutRequest.setSalary(7000);

        Customer customer = new Customer();
        customer.setIdentityNumber("1111111");
        customer.setFirstname("Ümit");
        customer.setLastname("Sayın");
        customer.setCreditScore(550);
        customer.setCollaterals(new ArrayList<>());
        customer.setSalary(7000);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber("1111111");
        customerDto.setFirstname("Ümit");
        customerDto.setLastname("Sayın");
        customerDto.setCreditScore(550);
        customerDto.setCollaterals(new ArrayList<>());
        customerDto.setSalary(7000);
        customerDto.setCollaterals(collateralList);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(collateralService.createCollateral(collateralRequest)).thenReturn(collateral);
        when(modelMapper.map(customerRepository.save(customer), CustomerDto.class)).thenReturn(customerDto);

        DataResponse<CustomerDto> result = customerService.updateCustomerById(String.valueOf(customerId), customerPutRequest);

        assertEquals(customerDto, result.getData());
    }

    @Test
    void testUpdateCustomerById_withCustomerId_thenThrowEntityNotFoundException(){
        UUID customerId = UUID.randomUUID();

        assertThrows(EntityNotFoundException.class, () -> customerService.updateCustomerById(String.valueOf(customerId), null));
    }

    @Test
    void testDeleteCustomerById_withCustomerId_thenThrowEntityNotFoundException(){
        UUID customerId = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setIdentityNumber("1111111");
        customer.setFirstname("Ümit");
        customer.setLastname("Sayın");
        customer.setCreditScore(550);
        customer.setCollaterals(new ArrayList<>());
        customer.setSalary(7000);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        customerService.deleteCustomerById(String.valueOf(customerId));
    }

    @Test
    void testGetCustomerById_withCustomerId_thenReturnCustomer(){
        UUID customerId = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setIdentityNumber("1111111");
        customer.setFirstname("Ümit");
        customer.setLastname("Sayın");
        customer.setCreditScore(550);
        customer.setCollaterals(new ArrayList<>());
        customer.setSalary(7000);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(customerId);

        assertEquals(customer, result);
    }

    @Test
    void testTotalCollateralPrice_withCollateralList_thenReturnDouble(){
        List<Collateral> collateralList = new ArrayList<>();
        Collateral collateral = new Collateral();

        collateral.setCollateral(CollateralType.CAR);
        collateral.setCurrency("TL");
        collateral.setPrice(400000);
        collateralList.add(collateral);


        double result = customerService.totalCollateralPrice(collateralList);

        assertEquals(400000, result);
    }

    @Test
    void testSaveCustomerCredit_withCustomer(){
        Customer customer = new Customer();
        customer.setIdentityNumber("1111111");
        customer.setFirstname("Ümit");
        customer.setLastname("Sayın");
        customer.setCreditScore(550);
        customer.setCollaterals(new ArrayList<>());
        customer.setSalary(7000);

        customerService.saveCustomerCredit(customer);
    }

}