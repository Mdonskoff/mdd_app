package com.openclassrooms.mddapi.back.dto;

import com.openclassrooms.mddapi.back.model.Comments;
import com.openclassrooms.mddapi.back.model.Topics;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesDto {

    private int id;

    private String title;

    private Date date;

    private String contents;

    private int idTopic;

    private String labelTopic;

    private String username;

    private List<Comments> commentsList;


}
