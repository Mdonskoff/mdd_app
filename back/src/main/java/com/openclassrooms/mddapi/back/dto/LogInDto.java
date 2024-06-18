package com.openclassrooms.mddapi.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogInDto {
    private String id;
    private String password;


}
