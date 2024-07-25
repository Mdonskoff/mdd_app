package com.openclassrooms.mddapi.back.model;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Topics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String label;

    @Column(length = 500)
    private String description;


}
