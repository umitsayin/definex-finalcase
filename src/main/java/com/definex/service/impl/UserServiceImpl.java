package com.definex.service.impl;

import com.definex.api.request.UserPostRequest;
import com.definex.api.request.UserPutRequest;
import com.definex.api.response.BaseResponse;
import com.definex.api.response.DataResponse;
import com.definex.api.response.SuccessDataResponse;
import com.definex.api.response.SuccessResponse;
import com.definex.constant.GlobalConstant;
import com.definex.dto.UserDto;
import com.definex.exception.EntityNotFoundException;
import com.definex.model.User;
import com.definex.repository.UserRepository;
import com.definex.service.RoleService;
import com.definex.service.UserService;
import com.definex.util.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DataResponse<UserDto> createUser(UserPostRequest userPostRequest) {
        final User user = User.builder()
                .firstname(userPostRequest.getFirstname())
                .lastname(userPostRequest.getLastname())
                .email(userPostRequest.getEmail())
                .password(passwordEncoder.encode(userPostRequest.getPassword()))
                .roles(new ArrayList<>())
                .build();

        user.getRoles().add(roleService.getRoleByName(RoleType.ADMIN.getValue()));

        final UserDto userDto = modelMapper.map(userRepository.save(user), UserDto.class);

        log.info("A new admin has been created");

        return new SuccessDataResponse<UserDto>(GlobalConstant.CREATED_USER_MESSAGE,userDto);
    }

    @Override
    public DataResponse<UserDto> updateUserById(String userId, UserPutRequest userPutRequest) {
        final User user = findUserById(UUID.fromString(userId));

        user.setFirstname(userPutRequest.getFirstname());
        user.setLastname(userPutRequest.getLastname());
        user.setPassword(passwordEncoder.encode(userPutRequest.getPassword()));

        final UserDto userDto = modelMapper.map(userRepository.save(user), UserDto.class);

        return new SuccessDataResponse<UserDto>(GlobalConstant.UPDATED_USER_MESSAGE,userDto);
    }

    @Override
    public BaseResponse deleteUserById(String userId) {
        final User user = findUserById(UUID.fromString(userId));

        userRepository.delete(user);

        return new SuccessResponse(GlobalConstant.DELETED_USER_MESSAGE);
    }

    private User findUserById(UUID userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException(GlobalConstant.USER_NOT_FOUND));
    }
}
