package com.example.hw3_rest_service.model;

import com.example.hw3_rest_service.entity.Claim;
import com.example.hw3_rest_service.model.dto.ClaimDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseClaimList extends ResponseBase {
    private int total;
    private List<ClaimDto> claimList;

    public ResponseClaimList(List<ClaimDto> claimList) {
        this.total = claimList.size();
        this.setSuccess(true);
        this.claimList = claimList;
    }
}
