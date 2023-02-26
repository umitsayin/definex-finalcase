package com.definex.api.response;

import lombok.Data;

public class SuccessResponse extends BaseResponse{
    public SuccessResponse(String message) {
        super(message, true);
    }
}
