package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PlanTypeResponse(
        UUID id,
        String code,
        String name,
        String description
) {
}
