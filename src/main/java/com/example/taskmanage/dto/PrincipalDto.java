package com.example.taskmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrincipalDto {

    private long userId;
    private String userName;
}
