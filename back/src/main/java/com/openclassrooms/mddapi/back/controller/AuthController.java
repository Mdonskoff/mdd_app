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
            description = "Send a Register object (username, email, password) to sign up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully registered", content = {@Content(schema = @Schema(implementation = RegisterDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
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
            description = "Obtain an access token by sending a Login object (email and password) to log in")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully logged in",
                    content = {@Content(schema = @Schema(implementation = ResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Login not valid",
                    content = {@Content(mediaType = "application/json")})
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
            description = "Get information about the logged-in user account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ResponseDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not logged in",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/me")
    public ResponseEntity<ResponseDto> getMe() {
        ResponseDto responseDto = new ResponseDto();
        UsersDto usersDto = authService.getMyInfo();
        responseDto.createResponse("me", usersDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Logout from MDD",
            description = "Logs out the user from the MDD application"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully logged out"),
            @ApiResponse(responseCode = "400", description = "Logout failed due to invalid request")
    })
    @GetMapping("/logout")
    public void logout(){
        authService.logout();
    }
}
