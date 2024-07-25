package com.openclassrooms.mddapi.back.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Articles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private Date date;

    @Column(length = 1000)
    private String contents;

    @ManyToOne
    private Users user;

    @OneToMany
    private List<Comments> commentsList;

    @ManyToOne
    private Topics topics;

}
