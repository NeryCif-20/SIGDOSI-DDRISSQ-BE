package com.ddrissq.sigdosi.healthcarenetwork.riss.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RissCreateRequest(
        @NotNull
        UUID dms,
        @NotBlank
        @Size(max = 50)
        String name
) {
}
