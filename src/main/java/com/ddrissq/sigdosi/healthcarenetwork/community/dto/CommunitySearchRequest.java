package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

public record CommunitySearchRequest(
        String name,
        String rissName,
        String dmsName,
        Integer territory,
        String sector,
        Long minPopulation,
        Long maxPopulation
) {
}
