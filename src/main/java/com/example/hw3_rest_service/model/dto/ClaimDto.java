package com.example.hw3_rest_service.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ClaimDto {
    private int id;
    private String description;
    private UserDto user;
    private List<ProductDto> productList;
}
