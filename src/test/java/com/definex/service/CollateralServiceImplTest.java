package com.definex.service;

import com.definex.api.request.CustomerCollateralRequest;
import com.definex.model.Collateral;
import com.definex.repository.CollateralRepository;
import com.definex.service.impl.CollateralServiceImpl;
import com.definex.util.CollateralType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CollateralServiceImplTest {
    private CollateralRepository collateralRepository;
    private CollateralService collateralService;

    @BeforeEach
    public void setUp(){
        collateralRepository = mock(CollateralRepository.class);
        collateralService = new CollateralServiceImpl(collateralRepository);
    }

    @Test
    void testCreateCollateral_withCollateralRequest_thenReturnCollateral(){
        CustomerCollateralRequest request = new CustomerCollateralRequest();
        request.setCollateral("CAR");
        request.setCurrency("TL");
        request.setPrice(400000);

        Collateral collateral = new Collateral();
        collateral.setCollateral(CollateralType.CAR);
        collateral.setCurrency("TL");
        collateral.setPrice(400000);

        when(collateralRepository.save(collateral)).thenReturn(collateral);

        Collateral result = collateralService.createCollateral(request);

        assertEquals(collateral,result);

    }
}