package com.ddrissq.sigdosi.healthcarenetwork.dms.dto;

import jakarta.validation.constraints.Size;

public record DmsUpdateRequest(
        @Size(max = 50)
        String name
) {
}
