package com.ddrissq.sigdosi.healthcarenetwork.dms.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DmsCreateRequest(
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String name
) {
}
