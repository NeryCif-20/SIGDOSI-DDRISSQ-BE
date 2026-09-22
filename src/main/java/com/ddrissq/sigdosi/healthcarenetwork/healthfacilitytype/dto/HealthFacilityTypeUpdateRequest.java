package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.Size;

public record HealthFacilityTypeUpdateRequest(
        @Size(min = 5, max = 10, message = ValidationError.SIZE)
        String code,
        @Size(min = 1, max = 25, message = ValidationError.SIZE)
        String name
) {
}
