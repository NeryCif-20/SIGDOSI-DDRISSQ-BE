package com.ddrissq.sigdosi.healthcarenetwork.dms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DmsCreateRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El tamaño maximo del nombre es de {max} caracteres")
        String name
) {
}
