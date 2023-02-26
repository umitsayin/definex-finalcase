package com.definex.service;

import com.definex.api.request.UserPostRequest;
import com.definex.api.request.UserPutRequest;
import com.definex.api.response.BaseResponse;
import com.definex.api.response.DataResponse;
import com.definex.api.response.SuccessResponse;
import com.definex.constant.GlobalConstant;
import com.definex.dto.UserDto;
import com.definex.exception.EntityNotFoundException;
import com.definex.model.Role;
import com.definex.model.User;
import com.definex.repository.UserRepository;
import com.definex.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    public void setUp(){
        modelMapper = mock(ModelMapper.class);
        userRepository = mock(UserRepository.class);
        roleService = mock(RoleService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(modelMapper,userRepository,roleService,passwordEncoder);
    }

    @Test
    void testCreateUser_WithUserPostRequest_thenReturnUserDto(){
        Role role = new Role("ADMIN");
        UserPostRequest request = new UserPostRequest();
        request.setFirstname("Ümit");
        request.setLastname("Sayın");
        request.setEmail("test@test.com");
        request.setPassword("test");

        User user = new User();

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(user.getPassword());

        UserDto userDto = new UserDto();
        userDto.setFirstname(request.getFirstname());
        userDto.setLastname(request.getLastname());
        userDto.setEmail(request.getEmail());

        when(roleService.getRoleByName("ADMIN")).thenReturn(role);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(modelMapper.map(userRepository.save(user), UserDto.class)).thenReturn(userDto);

        DataResponse<UserDto> result = userService.createUser(request);

        assertEquals(userDto, result.getData());
    }

    @Test
    void testUpdateUser_WithUserIdAndUserPutRequest_thenReturnUserDto(){
        UUID userId = UUID.randomUUID();
        UserPutRequest request = new UserPutRequest();
        request.setFirstname("Ümit");
        request.setLastname("Sayın");
        request.setPassword("test");

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail("test@test.com");
        user.setPassword(user.getPassword());

        UserDto userDto = new UserDto();
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(modelMapper.map(userRepository.save(user), UserDto.class)).thenReturn(userDto);

        DataResponse<UserDto> result = userService.updateUserById(String.valueOf(userId),request);

        assertEquals(userDto, result.getData());
    }

    @Test
    void testUpdateUser_WithUserIdAndUserPutRequest_thenThrowEntityNotFoundException(){
        UUID userId = UUID.randomUUID();
        UserPutRequest request = new UserPutRequest();
        request.setFirstname("Ümit");
        request.setLastname("Sayın");
        request.setPassword("test");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> userService.updateUserById(String.valueOf(userId),request));
    }

    @Test
    void testDeleteUser_WithUserId_thenReturnBaseResponse(){
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setFirstname("ümit");
        user.setLastname("sayın");
        user.setEmail("test@test.com");
        user.setPassword(user.getPassword());

        SuccessResponse response = new SuccessResponse(GlobalConstant.DELETED_USER_MESSAGE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        BaseResponse result = userService.deleteUserById(String.valueOf(userId));

        assertEquals(response, result);
    }

    @Test
    void testDeleteUser_WithUserId_thenThrowEntityNotFoundException(){
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> userService.deleteUserById(String.valueOf(userId)));
    }
}