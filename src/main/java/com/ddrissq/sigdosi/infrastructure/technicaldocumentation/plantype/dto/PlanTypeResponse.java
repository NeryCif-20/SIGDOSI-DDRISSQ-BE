package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto;

import java.util.UUID;

public record PlanTypeResponse(
        UUID id,
        String code,
        String name,
        String description
) {
}
