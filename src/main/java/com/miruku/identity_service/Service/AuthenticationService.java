package com.miruku.identity_service.Service;

import com.miruku.identity_service.Exception.AppException;
import com.miruku.identity_service.Exception.ErrorCode;
import com.miruku.identity_service.Repository.UserRepository;
import com.miruku.identity_service.dto.Request.AuthenticationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(@NotNull AuthenticationRequest request){
        var targetUser = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.NO_EXISTED_USERNAME));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(request.getPassword(), targetUser.getPassword());
    }
}
