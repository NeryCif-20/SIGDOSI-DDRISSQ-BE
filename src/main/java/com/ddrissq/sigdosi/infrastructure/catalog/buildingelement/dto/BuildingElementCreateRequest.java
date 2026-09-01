package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BuildingElementCreateRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El tamaño maximo del nombre es de {max} caracteres")
        String name,
        @NotNull(message = "Es obligatorio especificar si requiere un material")
        Boolean hasBuildingMaterial
) {
}
