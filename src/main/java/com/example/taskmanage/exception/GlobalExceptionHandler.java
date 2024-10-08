package com.example.taskmanage.exception;

import com.example.taskmanage.dto.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<BaseResponse> handleAllException(RuntimeException exception) {

        ErrorCode errorCode = ErrorCode.UN_CATEGORY;

        log.error("Exception ", exception);

        BaseResponse responseDto = BaseResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumKey);


        } catch (IllegalArgumentException e) {

            log.warn(e.getMessage());
        }

        BaseResponse responseDto = BaseResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<BaseResponse> handleBaseException(BaseException exception) {

        ErrorCode errorCode = exception.getErrorCode();

        BaseResponse responseDto = BaseResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getCode()).body(responseDto);
    }
}
