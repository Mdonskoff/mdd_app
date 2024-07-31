package com.openclassrooms.mddapi.back.controller;

import com.openclassrooms.mddapi.back.dto.*;
import com.openclassrooms.mddapi.back.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
            summary = "Sign up to the app and return a token",
            description = "Send a Register object (name, email, password) to sign up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = RegisterDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "400")
    })
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

    @Operation(
            summary = "Login to the app and get a token",
            description = "Get a token access by sending a Login object to login ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = LogInDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "400")
    })
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

    @Operation(
            summary = "Get user info",
            description = "Get informations about the logged user account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
    @GetMapping("/me")
    public ResponseEntity<ResponseDto> getMe() {
        ResponseDto responseDto = new ResponseDto();
        UsersDto usersDto = authService.getMyInfo();
        responseDto.createResponse("me", usersDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Log out to MDD",
            description = "MDD application logout")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @GetMapping("/logout")
    public void logout(){
        authService.logout();
    }
}
