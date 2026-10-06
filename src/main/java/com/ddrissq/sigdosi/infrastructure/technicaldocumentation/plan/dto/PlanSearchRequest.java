package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PlanSearchRequest(
        UUID type,
        UUID healthFacility,
        Short version
) {
}
