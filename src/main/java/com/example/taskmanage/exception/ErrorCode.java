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
    ASSIGN_TASK_NAME(1009, "Assignee of task must be not null", HttpStatus.BAD_REQUEST),
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
