package com.example.hw3_rest_service.model;

import com.example.hw3_rest_service.model.dto.ClaimDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseAddClaim extends ResponseBase {
    private ClaimDto claimDto;

    public ResponseAddClaim(ClaimDto claimDto) {
        this.claimDto = claimDto;
        this.setSuccess(true);
    }

    public ResponseAddClaim(String error) {
        this.setError(error);
        this.setSuccess(false);
    }
}
