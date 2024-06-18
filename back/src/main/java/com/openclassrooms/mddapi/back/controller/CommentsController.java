package com.openclassrooms.mddapi.back.controller;

import com.openclassrooms.mddapi.back.dto.ArticlesDto;
import com.openclassrooms.mddapi.back.dto.CommentsDto;
import com.openclassrooms.mddapi.back.dto.ResponseDto;
import com.openclassrooms.mddapi.back.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    CommentsService commentsService;

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
