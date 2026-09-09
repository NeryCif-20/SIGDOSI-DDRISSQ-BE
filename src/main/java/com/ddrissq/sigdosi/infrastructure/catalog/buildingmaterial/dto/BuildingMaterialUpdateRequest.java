package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto;

import jakarta.validation.constraints.Size;

public record BuildingMaterialUpdateRequest(
        @Size(max = 10)
        String code,
        @Size(max = 35)
        String name
) {
}
