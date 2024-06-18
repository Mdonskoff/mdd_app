package com.openclassrooms.mddapi.back.controller;

import com.openclassrooms.mddapi.back.dto.*;
import com.openclassrooms.mddapi.back.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> LogIn(@RequestBody LogInDto logInDto) {
        ResponseDto responseDto = new ResponseDto();
        String token = authService.logIn(logInDto);
        if(token.isEmpty()){
            responseDto.createResponse("error","Login not valid");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
        }
        responseDto.createResponse("token",authService.logIn(logInDto));
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseDto> getMe() {
        ResponseDto responseDto = new ResponseDto();
        UsersDto usersDto = authService.getMyInfo();
        responseDto.createResponse("me", usersDto);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody RegisterDto registerDto) {
        boolean is_created = authService.createUser(registerDto);
        ResponseDto responseDto = new ResponseDto();
        if(is_created) {
            responseDto.createResponse("success", true);
            return ResponseEntity.ok(responseDto);
        }
        responseDto.createResponse("success", false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }
}
