package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto;

import lombok.Builder;

@Builder
public record PlanTypeSearchRequest(
        String q
) {
}
