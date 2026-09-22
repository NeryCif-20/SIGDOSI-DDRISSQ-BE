package com.ddrissq.sigdosi.healthcarenetwork.riss.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RissCreateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        UUID dms,
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String name
) {
}
