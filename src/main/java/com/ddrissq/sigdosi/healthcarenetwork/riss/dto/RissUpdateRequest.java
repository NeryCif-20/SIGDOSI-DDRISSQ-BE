package com.ddrissq.sigdosi.healthcarenetwork.riss.dto;

import java.util.UUID;

public record RissUpdateRequest(
        UUID dms,
        String name
) {
}
