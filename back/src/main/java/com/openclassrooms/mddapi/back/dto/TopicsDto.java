package com.openclassrooms.mddapi.back.dto;

import lombok.Data;

@Data
public class TopicsDto {
    private int id;

    private String label;

    private int [] idTopicsTab;
}
