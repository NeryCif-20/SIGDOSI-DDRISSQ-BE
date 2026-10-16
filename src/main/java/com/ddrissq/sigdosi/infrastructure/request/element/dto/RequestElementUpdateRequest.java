package com.ddrissq.sigdosi.infrastructure.request.element.dto;

import com.ddrissq.sigdosi.infrastructure.request.element.model.RequestElementRequiredAction;

import java.util.UUID;

public record RequestElementUpdateRequest(
        UUID buildingElement,
        UUID buildingMaterial,
        RequestElementRequiredAction requiredAction
) {
}
