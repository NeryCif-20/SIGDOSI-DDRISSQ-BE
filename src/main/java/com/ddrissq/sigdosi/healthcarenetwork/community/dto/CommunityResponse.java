package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissResponse;

import java.util.UUID;

public record CommunityResponse(
        UUID id,
        RissResponse riss,
        String name,
        Integer territory,
        String sector,
        Long population
) {
}
