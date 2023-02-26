package com.definex.service;

import com.definex.api.request.UserPostRequest;
import com.definex.api.request.UserPutRequest;
import com.definex.api.response.BaseResponse;
import com.definex.api.response.DataResponse;
import com.definex.dto.UserDto;

public interface UserService {
    DataResponse<UserDto> createUser(UserPostRequest userPostRequest);
    DataResponse<UserDto> updateUserById(String userId, UserPutRequest userPutRequest);
    BaseResponse deleteUserById(String userId);
}
