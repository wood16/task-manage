package com.example.taskmanage.exception;

import com.example.taskmanage.utils.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<BaseResponseDto> handleAllException(RuntimeException exception) {

        log.error("Exception ", exception);

        BaseResponseDto responseDto = BaseResponseDto.builder()
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.valueOf(responseDto.getCode()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumKey);


        } catch (IllegalArgumentException e) {

            log.warn(e.getMessage());
        }

        BaseResponseDto responseDto = BaseResponseDto.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<BaseResponseDto> handleBaseException(BaseException exception) {
        BaseResponseDto responseDto = BaseResponseDto.builder()
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.valueOf(responseDto.getCode()));
    }
}
