package com.definex.util.helper;

import com.definex.constant.GlobalConstant;
import com.definex.exception.CreditNotIdentifiedException;
import com.definex.exception.InsufficientCreditLimitException;
import com.definex.model.Credit;
import com.definex.model.Customer;
import com.definex.service.CustomerService;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CreditHelper {

    public Credit calculateCreditByCustomer(Customer customer, CustomerService customerService, SystemHelper systemHelper){
        Credit credit = null;
        double totalCollateralPrice = customerService.totalCollateralPrice(customer.getCollaterals());

        if(customer.getCreditScore() < GlobalConstant.VALID_MINIMUM_CREDIT_LIMIT){
            throw new InsufficientCreditLimitException(GlobalConstant.ERROR_CREDIT_LIMIT);
        }else if(customer.getCreditScore() >= GlobalConstant.VALID_MINIMUM_CREDIT_LIMIT
                && customer.getCreditScore() < GlobalConstant.VALID_MAXIMUM_CREDIT_LIMIT
                && customer.getSalary() < GlobalConstant.MIDDLE_SALARY_TL){

            credit = createCreditByCreditAmount(GlobalConstant.SMALL_QUANTITY_LOAN_AMOUNT
                    + totalCollateralPrice * 0.1, systemHelper);

        }else if(customer.getCreditScore() >= GlobalConstant.VALID_MINIMUM_CREDIT_LIMIT
                && customer.getCreditScore() < GlobalConstant.VALID_MAXIMUM_CREDIT_LIMIT
                && customer.getSalary() >= GlobalConstant.MIDDLE_SALARY_TL
                && customer.getSalary() < GlobalConstant.HIGH_SALARY_TL){

            credit = createCreditByCreditAmount(GlobalConstant.MEDIUM_QUANTITY_LOAN_AMOUNT
                    + totalCollateralPrice * 0.20 , systemHelper);

        }
        else if(customer.getCreditScore() >= GlobalConstant.VALID_MINIMUM_CREDIT_LIMIT
                && customer.getCreditScore() < GlobalConstant.VALID_MAXIMUM_CREDIT_LIMIT
                && customer.getSalary() >= GlobalConstant.HIGH_SALARY_TL){

            credit = createCreditByCreditAmount((customer.getSalary()
                    * GlobalConstant.CREDIT_LIMIT_MULTIPLIER) / 2 + totalCollateralPrice * 0.25, systemHelper);

        }else if(customer.getCreditScore() >= GlobalConstant.VALID_MAXIMUM_CREDIT_LIMIT){
            credit = createCreditByCreditAmount(customer.getSalary()
                            * GlobalConstant.CREDIT_LIMIT_MULTIPLIER + totalCollateralPrice * 0.5, systemHelper);
        }else{
            throw new CreditNotIdentifiedException(GlobalConstant.CREDIT_NOT_IDENTIFIED);
        }

        return credit;
    }

    private Credit createCreditByCreditAmount(double creditAmount, SystemHelper systemHelper){
        return Credit.builder()
                .creditAmount(creditAmount)
                .currency(GlobalConstant.CURRENCY)
                .customerRepresentative(systemHelper.getCurrentUser())
                .build();
    }
}
