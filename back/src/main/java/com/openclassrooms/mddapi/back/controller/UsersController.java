package com.openclassrooms.mddapi.back.controller;

import com.openclassrooms.mddapi.back.dto.ResponseDto;
import com.openclassrooms.mddapi.back.dto.UsersDto;
import com.openclassrooms.mddapi.back.model.Users;
import com.openclassrooms.mddapi.back.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UsersController {

    @Autowired
    UsersService usersService;

    @PutMapping("/update")
    ResponseEntity<ResponseDto> updateUser(@RequestBody Users users) {

        UsersDto usersDto = new UsersDto();
        usersDto.setEmail(users.getEmail());
        usersDto.setPseudo(users.getPseudo());
        String password = users.getPassword();
        ResponseDto responseDto = new ResponseDto();
        String token = usersService.updateUser(usersDto, password);
        if (token.contains("exist") || token.contains("valid")) {
            responseDto.createResponse("error", token);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
        responseDto.createResponse("token", token);
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/topic/{idTopic}")
    ResponseEntity<ResponseDto> topicSubscription(@PathVariable int idTopic) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.createResponse("success", usersService.topicSubscription(idTopic));
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/topic/{idTopic}")
    ResponseEntity<ResponseDto> topicUnsubscription(@PathVariable int idTopic) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.createResponse("success", usersService.topicUnsubscription(idTopic));
        return ResponseEntity.ok(responseDto);
    }

}
