package com.example.taskmanage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_NULL(1002, "Username must be not null", HttpStatus.BAD_REQUEST),
    PASSWORD_NULL(1003, "Password must be not null", HttpStatus.BAD_REQUEST),
    ROLE_NULL(1004, "Roles must be not null", HttpStatus.BAD_REQUEST),
    UN_CATEGORY(9999, "Un category error", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
