package com.miruku.identity_service.Service;

import com.miruku.identity_service.Entity.User;
import com.miruku.identity_service.Repository.UserRepository;
import com.miruku.identity_service.dto.Request.UserCreationRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(@NotNull UserCreationRequest request){
        User user = new User();

        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        return userRepository.save(user);
    }
}
