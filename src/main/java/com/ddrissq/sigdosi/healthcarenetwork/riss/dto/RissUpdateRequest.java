package com.ddrissq.sigdosi.healthcarenetwork.riss.dto;

import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RissUpdateRequest(
        UUID dms,
        @NullableNotBlank
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String name
) {
}
