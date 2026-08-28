package com.ddrissq.sigdosi.healthcarenetwork.riss.dto;

import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsResponse;

import java.util.UUID;

public record RissResponse(
        UUID id,
        DmsResponse dms,
        String name
) {
}
