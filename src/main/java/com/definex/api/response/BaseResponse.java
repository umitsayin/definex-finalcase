package com.definex.api.response;

import lombok.Data;

@Data
public class BaseResponse {
    private String message;
    private boolean status;

    public BaseResponse(String message, boolean status){
        this.message = message;
        this.status = status;
    }
}
