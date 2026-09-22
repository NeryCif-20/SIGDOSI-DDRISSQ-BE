package com.ddrissq.sigdosi.healthcarenetwork.dms.dto;

import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.Size;

public record DmsUpdateRequest(
        @NullableNotBlank
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String name
) {
}
