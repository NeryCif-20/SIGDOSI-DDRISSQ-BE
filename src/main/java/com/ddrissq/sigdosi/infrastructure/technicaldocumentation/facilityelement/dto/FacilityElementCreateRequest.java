package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record FacilityElementCreateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        UUID healthFacility,
        @NotNull(message = ValidationError.REQUIRED)
        UUID buildingElement,
        @NotNull(message = ValidationError.REQUIRED)
        UUID buildingMaterial
) {
}
