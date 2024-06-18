package com.openclassrooms.mddapi.back.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentsDto {

    private int id;

    private String comments;

    private Date date;

    private int idArticle;

}
