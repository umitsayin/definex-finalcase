package com.definex.dto;

import lombok.Data;

@Data
public class AuthorizedPersonDto extends BaseDto {
    private String firstname;
    private String lastname;
    private String email;
}
