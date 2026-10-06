package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FacilityElementUpdateRequest(
        UUID healthFacility,
        UUID buildingElement,
        UUID buildingMaterial
) {
}
