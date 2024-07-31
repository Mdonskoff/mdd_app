package com.openclassrooms.mddapi.back.controller;

import com.openclassrooms.mddapi.back.dto.ArticlesDto;
import com.openclassrooms.mddapi.back.dto.ResponseDto;
import com.openclassrooms.mddapi.back.dto.UsersDto;
import com.openclassrooms.mddapi.back.model.Users;
import com.openclassrooms.mddapi.back.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Operation(
            summary = "Get all articles by topics subscriptions",
            description = "Returns all articles subscribed by the user")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    @GetMapping("/articles")
    ResponseEntity<ResponseDto> getArticlesByTopicsSubscriptions() {

        ResponseDto responseDto = new ResponseDto();
        List<ArticlesDto> articlesDtoList = usersService.getArticlesByTopicsSubscriptions();

        if (articlesDtoList == null) {
            responseDto.createResponse("error", "Article not found");
            return ResponseEntity.ok(responseDto);
        }
        responseDto.createResponse("Articles", articlesDtoList);
        return ResponseEntity.ok(responseDto);
    }


    @Operation(
            summary = "Update the information of the user who is logged in ",
            description = "Returns a new token to identify the user")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Exception.class)))
    })
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

    @Operation(
            summary = "Update the topic subscription about user who is logged in",
            description = "Send id topic in the URL for update the user subscription")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    @PostMapping("/topic/{idTopic}")
    ResponseEntity<ResponseDto> topicSubscription(@PathVariable int idTopic) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.createResponse("success", usersService.topicSubscription(idTopic));
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Unsbuscribe a topic by the user",
            description = "Send id topic in the URL for update the user subscription")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    @DeleteMapping("/topic/{idTopic}")
    ResponseEntity<ResponseDto> topicUnsubscription(@PathVariable int idTopic) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.createResponse("success", usersService.topicUnsubscription(idTopic));
        return ResponseEntity.ok(responseDto);
    }

}
