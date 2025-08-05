package com.example.taskmanage.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseResponse<T> {

    @Builder.Default
    int code = 1000;
    @Builder.Default
    String message = "SUCCESS";
    T result;
}
