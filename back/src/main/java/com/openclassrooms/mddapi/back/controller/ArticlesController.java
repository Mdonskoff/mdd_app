package com.openclassrooms.mddapi.back.controller;

import com.openclassrooms.mddapi.back.dto.ArticlesDto;
import com.openclassrooms.mddapi.back.dto.ResponseDto;
import com.openclassrooms.mddapi.back.service.ArticlesService;
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
@RequestMapping("/api/articles")
public class ArticlesController {

    @Autowired
    private ArticlesService articlesService;

    @Operation(
            summary = "Create an article",
            description = "Creates a new article and returns the article object")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Article successfully created",
                    content = {@Content(schema = @Schema(implementation = ResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Article not created",
                    content = {@Content(schema = @Schema(implementation = ResponseDto.class), mediaType = "application/json")})
    })
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

    @Operation(
            summary = "Get all articles",
            description = "Returns a list of all articles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved articles",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "400", description = "Error retrieving articles",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @GetMapping("")
    private ResponseEntity<ResponseDto> getAllArticles() {
        List<ArticlesDto> articles = articlesService.getAllArticles();
        ResponseDto responseDto = new ResponseDto();
        responseDto.createResponse("articles", articles);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Get an article by Id",
            description = "Returns the article details for the specified article ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved article",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid article ID",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))}),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @GetMapping("/{idArticle}")
    private ResponseEntity<ResponseDto> getArticle(@PathVariable int idArticle) {
        ArticlesDto article = articlesService.getArticleById(idArticle);
        ResponseDto responseDto = new ResponseDto();
        responseDto.createResponse("article", article);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Get all articles by topic id",
            description = "Returns all articles that belong to a topic by passing the topic's ID in the URL")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved articles by topic",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid topic ID",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))}),
            @ApiResponse(responseCode = "404", description = "No articles found for the specified topic",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
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

    @Operation(
            summary = "Delete an article by Id",
            description = "Deletes the article with the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Article successfully deleted",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid article ID",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))}),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @DeleteMapping("/{idArticle}")
    public ResponseEntity<ResponseDto> deleteArticle(@PathVariable int idArticle) {
        ResponseDto responseDto = new ResponseDto();
        try {
            responseDto.createResponse("Success", "true");
            articlesService.delete(idArticle);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.createResponse("Success", "false");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
    }


}
