package com.openclassrooms.mddapi.back.controller;

import com.openclassrooms.mddapi.back.dto.ArticlesDto;
import com.openclassrooms.mddapi.back.dto.ResponseDto;
import com.openclassrooms.mddapi.back.dto.TopicsDto;
import com.openclassrooms.mddapi.back.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticlesController {

    @Autowired
    private ArticlesService articlesService;

    @PostMapping("")
    private ResponseEntity<ResponseDto> createArticle(@RequestBody ArticlesDto articleDto) {
        ArticlesDto article = articlesService.createArticle(articleDto);
        ResponseDto responseDto = new ResponseDto();
        if (article != null) {
            responseDto.createResponse("article", article);
            return ResponseEntity.ok(responseDto);
        }
        responseDto.createResponse("error", "Article not created");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);

    }

    @GetMapping("")
    private ResponseEntity<ResponseDto> getAllArticles() {
        List<ArticlesDto> articles = articlesService.getAllArticles();
        ResponseDto responseDto = new ResponseDto();
        responseDto.createResponse("articles", articles);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{idArticle}")
    private ResponseEntity<ResponseDto> getArticle(@PathVariable int idArticle) {
        ArticlesDto article = articlesService.getArticleById(idArticle);
        ResponseDto responseDto = new ResponseDto();
        responseDto.createResponse("article", article);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/topic/{idTopic}")
    private ResponseEntity<ResponseDto> getArticlesByTopicId(@PathVariable int idTopic) {
        List<ArticlesDto> articles = articlesService.getArticlesByIdTopic(idTopic);
        ResponseDto responseDto = new ResponseDto();
        if (articles != null) {
            responseDto.createResponse("articles", articles);
            return ResponseEntity.ok(responseDto);
        }
        responseDto.createResponse("error", "Article not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);

    }

    @DeleteMapping("/{idArticle}")
    public ResponseEntity<ResponseDto> deleteArticle(@PathVariable int idArticle) {
        ResponseDto responseDto = new ResponseDto();
        try {
            responseDto.createResponse("success", "true");
            articlesService.delete(idArticle);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.createResponse("success", "false");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
    }


}
