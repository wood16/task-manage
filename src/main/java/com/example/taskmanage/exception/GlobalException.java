package com.example.taskmanage.exception;

import com.example.taskmanage.utils.BaseResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponseDto> handleBaseException(BaseException e){
        BaseResponseDto responseDto = BaseResponseDto.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.ok(responseDto);

    }
}
