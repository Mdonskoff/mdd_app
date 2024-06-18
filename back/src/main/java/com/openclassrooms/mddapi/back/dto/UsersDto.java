package com.openclassrooms.mddapi.back.dto;

import com.openclassrooms.mddapi.back.model.Topics;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersDto {

    private int id;

    private String pseudo;

    private String email;

    private List<Topics> topicsList;

    private Date created_at;

    private Date updated_at;
}
