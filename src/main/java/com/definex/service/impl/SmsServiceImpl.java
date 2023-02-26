package com.definex.service.impl;

import com.definex.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    @Override
    public void sendSms(String phone, String message) {
        log.info("Sms sended -  Phone: {0}, Message: {1}", phone, message);
    }
}
