package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto;

import java.util.UUID;

public record FacilityElementSearchRequest(
        String q,
        UUID healthFacility,
        UUID buildingMaterial
) {
}
