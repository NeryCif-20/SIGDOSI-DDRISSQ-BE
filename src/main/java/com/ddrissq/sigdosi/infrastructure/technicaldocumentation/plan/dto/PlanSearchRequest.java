package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto;

import java.util.UUID;

public record PlanSearchRequest(
        UUID type,
        UUID healthFacility,
        Short version
) {
}
