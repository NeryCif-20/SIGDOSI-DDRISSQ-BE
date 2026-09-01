package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HealthFacilityTypeCreateRequest(
        @NotBlank(message = "El codigo es obligatorio")
        @Size(max = 10, message = "El tamaño codigo del nombre es de {max} caracteres")
        String code,
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 25, message = "El tamaño maximo del nombre es de {max} caracteres")
        String name
) {
}
