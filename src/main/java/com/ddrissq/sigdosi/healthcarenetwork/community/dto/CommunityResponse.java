package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissResponse;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CommunityResponse(
        UUID id,
        RissResponse riss,
        String name,
        Integer territory,
        String sector,
        Long population
) {
}
