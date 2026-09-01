package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto;

import jakarta.validation.constraints.Size;

public record BuildingElementUpdateRequest(
        @Size(max = 50, message = "El tamaño maximo del nombre es de {max} caracteres")
        String name,
        Boolean hasBuildingMaterial
) {
}
