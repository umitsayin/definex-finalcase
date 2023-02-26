package com.definex.dto;

import com.definex.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDto extends BaseDto{
    private String firstname;
    private String lastname;
    private String email;
    private List<Role> roles;
}
