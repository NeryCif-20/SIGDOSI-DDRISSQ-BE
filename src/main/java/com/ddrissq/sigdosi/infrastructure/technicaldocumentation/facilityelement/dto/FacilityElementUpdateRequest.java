package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto;

import java.util.UUID;

public record FacilityElementUpdateRequest(
        UUID healthFacility,
        UUID buildingElement,
        UUID buildingMaterial
) {
}
