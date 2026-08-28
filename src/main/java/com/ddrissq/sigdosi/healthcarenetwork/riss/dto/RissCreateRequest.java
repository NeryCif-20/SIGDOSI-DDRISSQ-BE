package com.ddrissq.sigdosi.healthcarenetwork.riss.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RissCreateRequest(
        @NotNull(message = "El dms es obligatorio")
        UUID dms,
        @NotBlank(message = "El nombre es obligatorio")
        String name
) {
}
