package com.definex.service.impl;

import com.definex.api.request.CustomerCollateralRequest;
import com.definex.model.Collateral;
import com.definex.repository.CollateralRepository;
import com.definex.service.CollateralService;
import com.definex.util.CollateralType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CollateralServiceImpl implements CollateralService {
    private final CollateralRepository collateralRepository;

    public CollateralServiceImpl(CollateralRepository collateralRepository) {
        this.collateralRepository = collateralRepository;
    }

    @Override
    public Collateral createCollateral(CustomerCollateralRequest customerCollateralRequest) {
        final Collateral collateral = new Collateral(CollateralType.valueOf(customerCollateralRequest.getCollateral()),
                customerCollateralRequest.getPrice(),
                customerCollateralRequest.getCurrency());

        log.info("Collateral created for customer.");
        return collateralRepository.save(collateral);
    }
}
