package com.definex.service;

import com.definex.api.request.CustomerCollateralRequest;
import com.definex.model.Collateral;

public interface CollateralService {
    Collateral createCollateral(CustomerCollateralRequest customerCollateralRequest);
}
