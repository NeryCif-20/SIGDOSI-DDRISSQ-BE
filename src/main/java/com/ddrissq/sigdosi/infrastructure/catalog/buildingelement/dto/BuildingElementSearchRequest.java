package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto;

public record BuildingElementSearchRequest(
        String q,
        Boolean hasBuildingMaterial
) {
}
