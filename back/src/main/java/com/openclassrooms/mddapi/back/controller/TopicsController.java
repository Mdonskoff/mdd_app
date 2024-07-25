package com.openclassrooms.mddapi.back.controller;

import com.openclassrooms.mddapi.back.dto.ResponseDto;
import com.openclassrooms.mddapi.back.dto.TopicsDto;
import com.openclassrooms.mddapi.back.service.TopicService;
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

    @GetMapping("")
    public ResponseEntity<ResponseDto> getAllTopics() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.createResponse("Topics", topicService.getAll());
        return ResponseEntity.ok(responseDto);
    }

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
