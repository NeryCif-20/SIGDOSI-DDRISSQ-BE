package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto;

import java.util.UUID;

public record BuildingMaterialResponse(
        UUID id,
        String code,
        String name
) {
}
