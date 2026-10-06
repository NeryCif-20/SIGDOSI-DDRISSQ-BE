package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FacilityElementSearchRequest(
        String q,
        UUID healthFacility,
        UUID buildingMaterial
) {
}
