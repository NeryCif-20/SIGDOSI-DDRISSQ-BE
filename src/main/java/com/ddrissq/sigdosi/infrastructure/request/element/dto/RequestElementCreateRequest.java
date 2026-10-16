package com.ddrissq.sigdosi.infrastructure.request.element.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.infrastructure.request.element.model.RequestElementRequiredAction;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RequestElementCreateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        UUID buildingElement,
        @NotNull(message = ValidationError.REQUIRED)
        UUID buildingMaterial,
        @NotNull(message = ValidationError.REQUIRED)
        RequestElementRequiredAction requiredAction
) {
}
