package com.example.hw3_rest_service.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private int id;
    private String name;
    private String email;
}
