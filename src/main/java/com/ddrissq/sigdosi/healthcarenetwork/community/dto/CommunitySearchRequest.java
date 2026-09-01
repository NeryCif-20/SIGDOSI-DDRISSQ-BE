package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

public record CommunitySearchRequest(
        String q,
        Integer territory,
        String sector,
        Long minPopulation,
        Long maxPopulation
) {
}
