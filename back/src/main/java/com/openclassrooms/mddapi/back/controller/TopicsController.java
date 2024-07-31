package com.openclassrooms.mddapi.back.controller;

import com.openclassrooms.mddapi.back.dto.ResponseDto;
import com.openclassrooms.mddapi.back.dto.TopicsDto;
import com.openclassrooms.mddapi.back.service.TopicService;
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

@Slf4j
@RestController
@RequestMapping("/api/topic")
public class TopicsController {

    @Autowired
    private TopicService topicService;

    @Operation(
            summary = "Get all topics",
            description = "Returns an array of topics")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    @GetMapping("")
    public ResponseEntity<ResponseDto> getAllTopics() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.createResponse("Topics", topicService.getAll());
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Create a topic",
            description = "Create a topic and return it")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    @PostMapping("")
    public ResponseEntity<ResponseDto> createTopic(@RequestBody TopicsDto topic) {
        ResponseDto responseDto = new ResponseDto();
        TopicsDto newTopic = topicService.createTopic(topic);
        if(newTopic != null) {
            responseDto.createResponse("Topic", newTopic);
            return ResponseEntity.ok(responseDto);
        }
        responseDto.createResponse("error", "Topic not created");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
