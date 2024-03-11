package com.example.hw3_rest_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RequestUpdateClaim {
    private Integer id;
    private String description;
    private Integer user_id;
    private List<Integer> productId;
}
