package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto;

import jakarta.validation.constraints.Size;

public record HealthFacilityTypeUpdateRequest(
        @Size(max = 10)
        String code,
        @Size(max = 25)
        String name
) {
}
