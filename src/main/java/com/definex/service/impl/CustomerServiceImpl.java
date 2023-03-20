package com.definex.service.impl;

import com.definex.api.request.CustomerPostRequest;
import com.definex.api.request.CustomerPutRequest;
import com.definex.api.response.BaseResponse;
import com.definex.api.response.DataResponse;
import com.definex.api.response.SuccessDataResponse;
import com.definex.api.response.SuccessResponse;
import com.definex.constant.GlobalConstant;
import com.definex.dto.CustomerDto;
import com.definex.exception.EntityNotFoundException;
import com.definex.model.Collateral;
import com.definex.model.Customer;
import com.definex.model.User;
import com.definex.repository.CustomerRepository;
import com.definex.service.CollateralService;
import com.definex.service.CustomerService;
import com.definex.util.helper.SystemHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final CollateralService collateralService;
    private final SystemHelper systemHelper;

    public CustomerServiceImpl(ModelMapper modelMapper,
                               CustomerRepository customerRepository,
                               CollateralService collateralService,
                               SystemHelper systemHelper) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.collateralService = collateralService;
        this.systemHelper = systemHelper;
    }

    @Override
    public DataResponse<List<CustomerDto>> getAll(){
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = customerList.stream()
                .map(customer -> modelMapper.map(customer,CustomerDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResponse<>("Customer listed.",customerDtoList);
    }

    @Override
    public DataResponse<CustomerDto> createCustomer(CustomerPostRequest customerPostRequest) {
        final User user = systemHelper.getCurrentUser();
        final List<Collateral> collaterals = customerPostRequest.getCollaterals().stream()
                .map(collateralService::createCollateral).collect(Collectors.toList());
        final Customer customer =Customer.builder()
                .identityNumber(customerPostRequest.getIdentityNumber())
                .firstname(customerPostRequest.getFirstname())
                .lastname(customerPostRequest.getLastname())
                .phone(customerPostRequest.getPhone())
                .salary(customerPostRequest.getSalary())
                .creditScore(calculateCustomerCreditScore(collaterals, customerPostRequest.getSalary()))
                .dateOfBirth(customerPostRequest.getDateOfBirth())
                .collaterals(collaterals)
                .user(user)
                .build();

        final CustomerDto customerDto = modelMapper.map(customerRepository.save(customer), CustomerDto.class);

        log.info("Customer successfully created");

        return new SuccessDataResponse<CustomerDto>(GlobalConstant.CREATED_CUSTOMER_MESSAGE,customerDto);
    }

    @Override
    public DataResponse<CustomerDto> updateCustomerById(String customerId, CustomerPutRequest customerPutRequest) {
        final Customer customer = findCustomerById(UUID.fromString(customerId));
        final List<Collateral> collaterals = customerPutRequest.getCollaterals().stream()
                .map(collateralService::createCollateral).collect(Collectors.toList());

        customer.setPhone(customerPutRequest.getPhone());
        customer.setSalary(customerPutRequest.getSalary());
        collaterals.stream().forEach(collateral -> customer.getCollaterals().add(collateral));

        final CustomerDto customerDto = modelMapper.map(customerRepository.save(customer), CustomerDto.class);

        log.info("Customer successfully updated");

        return new SuccessDataResponse<CustomerDto>(GlobalConstant.UPDATED_CUSTOMER_MESSAGE,customerDto);
    }

    @Override
    public BaseResponse deleteCustomerById(String customerId) {
        final Customer customer = findCustomerById(UUID.fromString(customerId));

        customerRepository.delete(customer);
        log.info("Customer successfully deleted");

        return new SuccessResponse(GlobalConstant.DELETED_CUSTOMER_MESSAGE);
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return findCustomerById(id);
    }

    @Override
    public double totalCollateralPrice(List<Collateral> collaterals){
        return collaterals.stream().map(Collateral::getPrice).reduce(Double::sum).orElse(0d);
    }

    @Override
    public void saveCustomerCredit(Customer customer) {
        customerRepository.save(customer);
    }


    private Customer findCustomerById(UUID customerId){
        return customerRepository.findById(customerId)
                .orElseThrow(()-> new EntityNotFoundException(GlobalConstant.CUSTOMER_NOT_FOUND));
    }

    private int calculateCustomerCreditScore(List<Collateral> collaterals, double salary){
        final Random rand = new Random();
        final double collateralTotalPrice = totalCollateralPrice(collaterals);
        int startCreditScore = (int)((collateralTotalPrice * 0.001) + (salary * 0.01));

        log.info("credit score calculated");

        return rand.nextInt(1000) + Math.min(startCreditScore, 350);
    }
}
