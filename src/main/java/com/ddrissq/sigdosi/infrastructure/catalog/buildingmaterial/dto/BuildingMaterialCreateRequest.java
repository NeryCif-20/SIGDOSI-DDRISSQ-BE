package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BuildingMaterialCreateRequest(
        @NotBlank
        @Size(max = 10)
        String code,
        @NotBlank
        @Size(max = 35)
        String name
) {
}
