package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BuildingElementCreateRequest(
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String name,
        @NotNull(message = ValidationError.REQUIRED)
        Boolean hasBuildingMaterial
) {
}
