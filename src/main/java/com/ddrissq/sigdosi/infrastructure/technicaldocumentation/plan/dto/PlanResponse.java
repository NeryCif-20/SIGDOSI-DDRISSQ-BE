package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeResponse;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PlanResponse(
        UUID id,
        PlanTypeResponse type,
        String path,
        Short version,
        String notes
) {
}
