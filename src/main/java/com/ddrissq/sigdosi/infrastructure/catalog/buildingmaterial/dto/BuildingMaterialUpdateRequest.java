package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto;

import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.Size;

public record BuildingMaterialUpdateRequest(
        @NullableNotBlank
        @Size(min = 5, max = 10, message = ValidationError.SIZE)
        String code,
        @NullableNotBlank
        @Size(min = 1, max = 35, message = ValidationError.SIZE)
        String name
) {
}
