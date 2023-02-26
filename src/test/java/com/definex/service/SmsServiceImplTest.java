package com.definex.service;

import com.definex.service.impl.SmsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SmsServiceImplTest {
    private SmsService smsService;

    @BeforeEach
    public void setUp(){
        smsService = new SmsServiceImpl();
    }

    @Test
    void testSendSms_withPhoneAndMessage(){
        smsService.sendSms("555 555 55 55", "credit created!");
    }

}