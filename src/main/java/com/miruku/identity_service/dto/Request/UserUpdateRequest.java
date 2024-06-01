package com.miruku.identity_service.dto.Request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    @Size(min = 4, message = "username is more than 3")
    String username;
    @Size(min = 8, message = "password is more than 4")
    String password;

    String name;
}
