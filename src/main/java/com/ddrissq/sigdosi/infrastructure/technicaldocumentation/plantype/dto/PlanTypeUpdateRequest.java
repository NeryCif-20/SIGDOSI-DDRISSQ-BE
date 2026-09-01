package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto;

import jakarta.validation.constraints.Size;

public record PlanTypeUpdateRequest(
        @Size(max = 10, message = "El tamaño maximo del codigo es de {max} caracteres")
        String code,
        @Size(max = 35, message = "El tamaño maximo del nombre es de {max} caracteres")
        String name,
        String description
) {
}
