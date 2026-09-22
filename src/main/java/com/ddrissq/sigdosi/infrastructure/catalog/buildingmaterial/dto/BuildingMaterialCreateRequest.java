package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BuildingMaterialCreateRequest(
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 5, max = 10, message = ValidationError.SIZE)
        String code,
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 35, message = ValidationError.SIZE)
        String name
) {
}
