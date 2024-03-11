package com.example.hw3_rest_service.model;

import com.example.hw3_rest_service.model.dto.ClaimDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequestDeleteClaim {
    private ClaimDto claim;
}
