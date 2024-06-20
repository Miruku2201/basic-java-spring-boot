package com.miruku.identity_service.Exception;

import lombok.*;

//@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception"), // The exception is not define
    INVALID_MESSAGE(1001, "Invalid Message"), // The code of this exception do not define
    EXISTED_USER(1002, "Existed User"),
    NON_EXISTED_USER(1003, "User not found"),
    INVALID_USERNAME(1004, "Username must be at least 4 characters"),
    INVALID_PASSWORD(1005, "Password must be at least 8 characters"),
    NO_EXISTED_USERNAME(1006, "The username is not existed"),
    UNAUTHENTICATED(1007, "Unauthenticated");
    ;
    private int code;
    private String message;
}
