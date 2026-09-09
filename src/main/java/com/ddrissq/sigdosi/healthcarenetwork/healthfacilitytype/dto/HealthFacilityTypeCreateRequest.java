package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HealthFacilityTypeCreateRequest(
        @NotBlank
        @Size(max = 10)
        String code,
        @NotBlank
        @Size(max = 25)
        String name
) {
}
