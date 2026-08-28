package com.ddrissq.sigdosi.healthcarenetwork.dms.dto;

import jakarta.validation.constraints.NotBlank;

public record DmsCreateRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name
) {
}
