package com.miruku.identity_service.Controller;

import com.miruku.identity_service.Entity.User;
import com.miruku.identity_service.Service.UserService;
import com.miruku.identity_service.dto.Request.UserCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
     User createUser(@RequestBody UserCreationRequest request){
        return userService.createUser(request);
    }
}
