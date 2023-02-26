package com.definex.api.response;

import lombok.Data;

@Data
public class DataResponse <T>{
    private boolean status;
    private String message;
    private T data;

    public DataResponse(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
