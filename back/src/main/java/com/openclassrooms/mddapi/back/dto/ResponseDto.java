package com.openclassrooms.mddapi.back.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private HashMap<String, Object> data;

    public void createResponse(String id, Object value) {
        HashMap<String, Object> content = new HashMap<>();
        content.put(id, value);
        this.setData(content);
    }
}

