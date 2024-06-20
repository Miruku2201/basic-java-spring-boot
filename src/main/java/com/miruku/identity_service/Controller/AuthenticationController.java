package com.miruku.identity_service.Controller;


import com.miruku.identity_service.Service.AuthenticationService;
import com.miruku.identity_service.Service.UserService;
import com.miruku.identity_service.dto.Request.AuthenticationRequest;
import com.miruku.identity_service.dto.Request.IntrospectRequest;
import com.miruku.identity_service.dto.Response.ApiResponse;
import com.miruku.identity_service.dto.Response.AuthenticationResponse;
import com.miruku.identity_service.dto.Response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/users")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> log_in(@RequestBody AuthenticationRequest request) throws JOSEException {
        AuthenticationResponse result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .result(result)
                .build();
    }

    @PostMapping("/verify-token")
    ApiResponse<IntrospectResponse> verifyToken(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
        IntrospectResponse result = authenticationService.verifyToken(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(1000)
                .result(result)
                .build();
    }
}


