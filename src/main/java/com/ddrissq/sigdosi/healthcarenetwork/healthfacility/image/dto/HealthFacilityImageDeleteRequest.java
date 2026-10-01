package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record HealthFacilityImageDeleteRequest(
        @NotNull(message = ValidationError.REQUIRED)
        Set<@NotNull(message = ValidationError.REQUIRED) UUID> ids
) {
}
