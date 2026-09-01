package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto;

import java.util.UUID;

public record HealthFacilityTypeResponse(
        UUID id,
        String code,
        String name
) {
}
