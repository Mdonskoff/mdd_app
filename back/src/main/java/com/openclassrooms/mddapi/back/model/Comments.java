package com.openclassrooms.mddapi.back.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Data
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String comments;

    private Date date;
}
