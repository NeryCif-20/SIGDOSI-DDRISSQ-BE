package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeResponse;

import java.util.UUID;

public record PlanResponse(
        UUID id,
        PlanTypeResponse type,
        String path,
        Short version,
        String notes
) {
}
