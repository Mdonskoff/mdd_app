package com.openclassrooms.mddapi.back.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String email;

    private String pseudo;

    private String password;

}
