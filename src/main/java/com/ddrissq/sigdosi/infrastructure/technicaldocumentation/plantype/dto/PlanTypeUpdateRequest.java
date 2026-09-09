package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto;

import jakarta.validation.constraints.Size;

public record PlanTypeUpdateRequest(
        @Size(max = 10)
        String code,
        @Size(max = 35)
        String name,
        String description
) {
}
