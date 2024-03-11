package com.example.hw3_rest_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class ResponseBase {
    private boolean success;
    private String error;
}
