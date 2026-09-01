package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BuildingMaterialCreateRequest(
        @NotBlank(message = "El codigo es obligatorio")
        @Size(max = 10, message = "El tamaño maximo del codigo es de {max} caracteres")
        String code,
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 35, message = "El tamaño maximo del nombre es de {max} caracteres")
        String name
) {
}
