package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

import com.ddrissq.sigdosi.common.validation.annotation.CompareFields;
import com.ddrissq.sigdosi.common.validation.annotation.ComparisonOperator;

@CompareFields(first = "minPopulation", second = "maxPopulation", operator = ComparisonOperator.LESS_THAN)
public record CommunitySearchRequest(
        String q,
        Short territory,
        String sector,
        Long minPopulation,
        Long maxPopulation
) {
}
