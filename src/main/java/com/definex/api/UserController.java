package com.definex.api;

import com.definex.api.request.UserPostRequest;
import com.definex.api.request.UserPutRequest;
import com.definex.api.response.BaseResponse;
import com.definex.api.response.DataResponse;
import com.definex.dto.UserDto;
import com.definex.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<DataResponse<UserDto>> createUser(@RequestBody UserPostRequest userPostRequest){
        return ResponseEntity.ok(userService.createUser(userPostRequest));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<DataResponse<UserDto>> updateUserById(@PathVariable String userId,
                                                @RequestBody UserPutRequest userPutRequest){
        return ResponseEntity.ok(userService.updateUserById(userId,userPutRequest));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<BaseResponse> deleteUserById(@PathVariable String userId){
        return ResponseEntity.ok(userService.deleteUserById(userId));
    }
}
