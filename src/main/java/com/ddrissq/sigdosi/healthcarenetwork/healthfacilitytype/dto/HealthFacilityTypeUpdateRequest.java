package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto;

import jakarta.validation.constraints.Size;

public record HealthFacilityTypeUpdateRequest(
        @Size(max = 10, message = "El tamaño maximo del codigo es de {max} caracteres")
        String code,
        @Size(max = 25, message = "El tamaño maximo del nombre es de {max} caracteres")
        String name
) {
}
