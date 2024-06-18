package com.miruku.identity_service.Service;

import com.miruku.identity_service.Entity.User;
import com.miruku.identity_service.Exception.AppException;
import com.miruku.identity_service.Exception.ErrorCode;
import com.miruku.identity_service.Repository.UserRepository;
import com.miruku.identity_service.dto.Request.UserCreationRequest;
import com.miruku.identity_service.dto.Request.UserUpdateRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(@NotNull UserCreationRequest request){
        User user = new User();

        if (userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.EXISTED_USER);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(String userId){
        return userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.NON_EXISTED_USER));
    }

    public User updateUser(String userId, @NotNull UserUpdateRequest request){
        User targetUser = getUser(userId);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        targetUser.setPassword(passwordEncoder.encode(request.getPassword()));
        targetUser.setName(request.getName());
        return userRepository.save(targetUser);
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}
