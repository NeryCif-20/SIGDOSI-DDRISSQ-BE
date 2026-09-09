package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto;

import jakarta.validation.constraints.Size;

public record BuildingElementUpdateRequest(
        @Size(max = 50)
        String name,
        Boolean hasBuildingMaterial
) {
}
