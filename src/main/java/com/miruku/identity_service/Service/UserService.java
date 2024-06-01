package com.miruku.identity_service.Service;

import com.miruku.identity_service.Entity.User;
import com.miruku.identity_service.Repository.UserRepository;
import com.miruku.identity_service.dto.Request.UserCreationRequest;
import com.miruku.identity_service.dto.Request.UserUpdateRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(String userId){
        return userRepository.findById(userId).orElseThrow(()-> new RuntimeException("No id valid"));
    }

    public User updateUser(String userId, UserUpdateRequest request){
        User targetUser = getUser(userId);
        targetUser.setPassword(request.getPassword());
        targetUser.setName(request.getName());
        targetUser.setUsername(request.getUsername());
        return userRepository.save(targetUser);
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}
