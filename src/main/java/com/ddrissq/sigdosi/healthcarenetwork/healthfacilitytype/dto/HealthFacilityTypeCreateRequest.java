package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HealthFacilityTypeCreateRequest(
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 10, message = ValidationError.SIZE)
        String code,
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 25, message = ValidationError.SIZE)
        String name
) {
}
