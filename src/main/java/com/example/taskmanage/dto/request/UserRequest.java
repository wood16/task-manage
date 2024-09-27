package com.example.taskmanage.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {

    Long id;
    @NotNull(message = "USERNAME_NULL")
    String username;
    @NotNull(message = "PASSWORD_NULL")
    String password;
    @NotNull(message = "PASSWORD_NULL")
    String[] roles;
}
