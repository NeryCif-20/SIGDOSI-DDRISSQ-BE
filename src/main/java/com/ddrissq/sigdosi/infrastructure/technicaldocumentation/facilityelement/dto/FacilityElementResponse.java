package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto;

import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementResponse;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialResponse;

public record FacilityElementResponse(
        BuildingElementResponse buildingElement,
        BuildingMaterialResponse buildingMaterial
) {
}
