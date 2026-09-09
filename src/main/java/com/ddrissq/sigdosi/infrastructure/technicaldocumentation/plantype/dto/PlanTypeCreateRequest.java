package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PlanTypeCreateRequest(
        @NotBlank
        @Size(max = 10)
        String code,
        @NotBlank
        @Size(max = 35)
        String name,
        String description
) {
}
