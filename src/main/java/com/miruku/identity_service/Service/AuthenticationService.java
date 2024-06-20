package com.miruku.identity_service.Service;

import com.miruku.identity_service.Exception.AppException;
import com.miruku.identity_service.Exception.ErrorCode;
import com.miruku.identity_service.Repository.UserRepository;
import com.miruku.identity_service.dto.Request.AuthenticationRequest;
import com.miruku.identity_service.dto.Request.IntrospectRequest;
import com.miruku.identity_service.dto.Response.AuthenticationResponse;
import com.miruku.identity_service.dto.Response.IntrospectResponse;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    private UserRepository userRepository;
    @NonFinal
    protected static final String SIGN_KEY= "Vnr8qZbhLL+pHdEQJHL8RXvoA41EoP9knR7JDRxM9+LKKDNoX1+zkLg1VlghwXfU";

    private String generateToken(String username) throws JOSEException {
        // Create Header.
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Build Payload
            // Create claim set to contain the infomation
        JWTClaimsSet jwtClaimSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("miruku.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .build();
        Payload payload = new Payload(jwtClaimSet.toJSONObject());

        // Packing header, payload
        JWSObject jwsObject = new JWSObject(header, payload);

        // Build Signature
        try{
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            return jwsObject.serialize();
        }
        catch (JOSEException e){
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse verifyToken(@NotNull IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        // Verified Signature Key
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());

        // Parse Token
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Verify Token. Verify the parse token by signature key verifier.
        var verifiedToken = signedJWT.verify(verifier);

        // get expiration.
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        //The validation of JWT if remaining expiration and correct verification
        return IntrospectResponse.builder()
                .valid(expiryTime.after(new Date()) && verifiedToken)
                .build();
    }

    public AuthenticationResponse authenticate(@NotNull AuthenticationRequest request) throws JOSEException {
        var targetUser = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.NO_EXISTED_USERNAME));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean isAuth = passwordEncoder.matches(request.getPassword(), targetUser.getPassword());
        if (!isAuth){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(request.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }
}
