package com.example.hw3_rest_service.model;

import com.example.hw3_rest_service.model.dto.ClaimDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDeleteClaim extends ResponseBase {
    public ResponseDeleteClaim(boolean success) {
        this.setSuccess(success);
    }

    public ResponseDeleteClaim(String error) {
        this.setError(error);
        this.setSuccess(false);
    }
}
