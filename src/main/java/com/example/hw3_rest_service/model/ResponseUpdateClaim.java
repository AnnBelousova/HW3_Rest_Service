package com.example.hw3_rest_service.model;

import com.example.hw3_rest_service.model.dto.ClaimDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseUpdateClaim extends ResponseBase {
    private ClaimDto claimDto;

    public ResponseUpdateClaim(ClaimDto claimDto) {
        this.claimDto = claimDto;
        this.setSuccess(true);
    }

    public ResponseUpdateClaim(String error) {
        this.setError(error);
        this.setSuccess(false);
    }
}

