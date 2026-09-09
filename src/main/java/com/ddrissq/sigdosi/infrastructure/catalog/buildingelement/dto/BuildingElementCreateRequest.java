package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BuildingElementCreateRequest(
        @NotBlank
        @Size(max = 50)
        String name,
        @NotNull
        Boolean hasBuildingMaterial
) {
}
