package com.example.hw3_rest_service.model.dto;

import com.example.hw3_rest_service.entity.Claim;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ProductDto {
    private int id;
    private String name;
    private int weight;
    private List<ClaimDto> claimList;
}
