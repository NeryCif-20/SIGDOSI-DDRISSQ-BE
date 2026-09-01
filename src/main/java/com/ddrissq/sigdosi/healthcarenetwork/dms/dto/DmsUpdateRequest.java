package com.ddrissq.sigdosi.healthcarenetwork.dms.dto;

import jakarta.validation.constraints.Size;

public record DmsUpdateRequest(
        @Size(max = 50, message = "El tamaño maximo del nombre es de {max} caracteres")
        String name
) {
}
