package com.example.taskmanage.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_NULL(1002, "Username must be not null", HttpStatus.BAD_REQUEST),
    PASSWORD_NULL(1003, "Password must be not null", HttpStatus.BAD_REQUEST),
    ROLE_NULL(1004, "Roles must be not null", HttpStatus.BAD_REQUEST),
    TASK_NAME_NULL(1005, "Task name must be not null", HttpStatus.BAD_REQUEST),
    INVALID_TASK_NAME(1006, "Task name must be at least {min}", HttpStatus.BAD_REQUEST),
    START_DATE_TASK_NAME(1007, "Start date of task must be not null", HttpStatus.BAD_REQUEST),
    END_DATE_TASK_NAME(1008, "End date of task must be not null", HttpStatus.BAD_REQUEST),
    TASK_ASSIGN_NULL(1009, "Assignee of task must be not null", HttpStatus.BAD_REQUEST),
    TASK_NOT_FOUND(1010, "Task not found", HttpStatus.NOT_FOUND),
    START_END_DATE(1011, "Start date need before end date!", HttpStatus.BAD_REQUEST),
    CIRCULAR_RELATIONSHIP(1012, "Task have circular relationship, please check again!", HttpStatus.BAD_REQUEST),
    TOKEN_NOT_FOUND(1013, "Token not found", HttpStatus.NOT_FOUND),
    TOKEN_EXPIRATION(1014, "Token expiration", HttpStatus.UNAUTHORIZED),
    TOKEN_NOT_SUPPORT(1015, "Token's not supported", HttpStatus.UNAUTHORIZED),
    INVALID_PART_TOKEN(1016, "Invalid format 3 part of token", HttpStatus.UNAUTHORIZED),
    INVALID_FORMAT_TOKEN(1017, "Invalid format token", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRATION(1018, "Refresh token was expiration", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(1019, "User not found", HttpStatus.NOT_FOUND),
    INVALID_ROLE(1020, "Invalid role", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME_PASSWORD(1021, "Invalid username or password", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1022, "Role not found", HttpStatus.NOT_FOUND),
    INVALID_KEY(9998, "Invalid message key", HttpStatus.BAD_REQUEST),
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
