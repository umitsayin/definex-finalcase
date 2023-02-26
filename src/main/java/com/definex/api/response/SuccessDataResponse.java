package com.definex.api.response;

public class SuccessDataResponse <T> extends DataResponse{
    public SuccessDataResponse(String message, T data) {
        super(true, message, data);
    }
}
