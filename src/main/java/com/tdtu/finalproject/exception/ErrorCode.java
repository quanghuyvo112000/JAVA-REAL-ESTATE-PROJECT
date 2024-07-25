package com.tdtu.finalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    USER_EXISTED(1001, HttpStatus.BAD_REQUEST,"User existed"),
    EMAIL_EXISTED(1002, HttpStatus.BAD_REQUEST,"Email existed"),
    INVALID_KEY(1003, HttpStatus.BAD_REQUEST, "Invalid message key"),
    USERNAME_INVALID(1004, HttpStatus.BAD_REQUEST, "Username must be at least 8 characters"),
    PASSWORD_INVALID(1005, HttpStatus.BAD_REQUEST, "Password must be at least 8 characters"),
    EMAIL_INVALID(1006, HttpStatus.BAD_REQUEST,"Must be a well-formed email address"),
    USER_NOT_EXISTED(1007, HttpStatus.NOT_FOUND,"User not existed"),
    UNAUTHENTICATED(1008, HttpStatus.UNAUTHORIZED ,"Unauthenticated"),
    DEFAULT_ROLE_NOT_FOUND(1011, HttpStatus.BAD_REQUEST, "DEFAULT_ROLE_NOT_FOUND"),
    ROLE_NOT_EXISTED(1012, HttpStatus.BAD_REQUEST, "Role not found: USER"),
    UNCATEGORIZED_EXCEPTION(1009, HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized error"),
    POST_LIMIT_EXCEEDED(1013, HttpStatus.BAD_REQUEST, "POST_LIMIT_EXCEEDED"),
    REAL_ESTATE_NOT_FOUND(1014, HttpStatus.BAD_REQUEST, "REAL_ESTATE_NOT_FOUND"),
    EMAIL_NOT_EXISTED(1019, HttpStatus.BAD_REQUEST, "EMAIL_NOT_EXISTED"),
    INVALID_PASSWORD(1020, HttpStatus.BAD_REQUEST, "INVALID_PASSWORD"),
    UNAUTHORIZED(1010, HttpStatus.FORBIDDEN, "You do not have permission")

            ;


    private Integer code;
    private HttpStatusCode statusCode;
    private String message;
}
