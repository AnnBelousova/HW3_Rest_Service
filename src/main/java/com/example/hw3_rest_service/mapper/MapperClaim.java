package com.example.hw3_rest_service.mapper;

import com.example.hw3_rest_service.entity.Claim;
import com.example.hw3_rest_service.model.dto.ClaimDto;

public class MapperClaim {
    public ClaimDto entityToDto(Claim claim) {
        if (claim == null) {
            return null;
        }
        ClaimDto claimDto = new ClaimDto();
        claimDto.setId(claim.getId());
        claimDto.setDescription(claim.getDescription());
        return claimDto;
    }

    public Claim dtoToEntity(ClaimDto claimDto) {
        if (claimDto == null) {
            return null;
        }
        Claim claim = new Claim();
        claim.setId(claimDto.getId());
        claim.setDescription(claimDto.getDescription());
        return claim;
    }
}
