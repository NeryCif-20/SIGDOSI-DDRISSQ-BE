package com.ddrissq.sigdosi.infrastructure.request.element.dto;

import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementResponse;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialResponse;
import com.ddrissq.sigdosi.infrastructure.request.element.model.RequestElementRequiredAction;

public record RequestElementResponse(
        BuildingElementResponse buildingElement,
        BuildingMaterialResponse buildingMaterial,
        RequestElementRequiredAction requiredAction
) {
}
