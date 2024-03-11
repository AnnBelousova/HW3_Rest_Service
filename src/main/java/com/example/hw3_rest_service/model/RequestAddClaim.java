package com.example.hw3_rest_service.model;

import com.example.hw3_rest_service.model.dto.ClaimDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class RequestAddClaim {
    private String description;
    private Integer user_id;
    private List<Integer> productId;
}
