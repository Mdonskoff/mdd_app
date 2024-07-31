package com.openclassrooms.mddapi.back.controller;

import com.openclassrooms.mddapi.back.dto.CommentsDto;
import com.openclassrooms.mddapi.back.dto.ResponseDto;
import com.openclassrooms.mddapi.back.service.CommentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    CommentsService commentsService;

    @Operation(
            summary = "Post a comment",
            description = "Post a comment under an article. You must pass an object containing the comment and the post id")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = CommentsDto.class)))
    })
    @PostMapping("")
    private ResponseEntity<ResponseDto> createComment(@RequestBody CommentsDto commentDto) {
        CommentsDto comment =  commentsService.createComment(commentDto);
        ResponseDto responseDto = new ResponseDto();
        if(comment != null) {
            responseDto.createResponse("comment", comment);
            return ResponseEntity.ok(responseDto);
        }
        responseDto.createResponse("error", "Comment not created");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);

    }

}
