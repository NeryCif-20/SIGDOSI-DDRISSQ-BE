package com.ddrissq.sigdosi.healthcarenetwork.riss.dto;

import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RissUpdateRequest(
        UUID dms,
        @Size(max = 50)
        String name
) {
}
