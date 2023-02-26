package com.definex.api.request;

import lombok.Data;

@Data
public class UserPostRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
