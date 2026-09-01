package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto;

import java.util.UUID;

public record BuildingElementResponse(
        UUID id,
        String name,
        Boolean hasBuildingMaterial
) {
}
