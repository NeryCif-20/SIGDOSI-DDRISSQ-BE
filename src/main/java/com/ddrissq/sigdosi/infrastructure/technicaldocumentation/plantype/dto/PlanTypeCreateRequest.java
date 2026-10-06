package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto;

import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PlanTypeCreateRequest(
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 5, max = 10, message = ValidationError.SIZE)
        String code,
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 35, message = ValidationError.SIZE)
        String name,
        @NullableNotBlank
        String description
) {
}
