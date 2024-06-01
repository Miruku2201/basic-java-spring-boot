package com.miruku.identity_service.Controller;

import com.miruku.identity_service.Entity.User;
import com.miruku.identity_service.Service.UserService;
import com.miruku.identity_service.dto.Request.UserCreationRequest;
import com.miruku.identity_service.dto.Request.UserUpdateRequest;
import com.miruku.identity_service.dto.Response.ApiResponse;
import org.hibernate.jdbc.Expectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String userId){
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Delete user " + userId + "successfully");
        return apiResponse;
    }
}
