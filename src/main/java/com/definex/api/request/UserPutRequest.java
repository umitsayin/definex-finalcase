package com.definex.api.request;

import lombok.Data;

@Data
public class UserPutRequest {
    private String firstname;
    private String lastname;
    private String password;
}
