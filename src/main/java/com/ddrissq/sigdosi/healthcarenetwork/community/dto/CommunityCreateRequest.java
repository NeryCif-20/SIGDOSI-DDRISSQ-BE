package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.healthcarenetwork.community.error.CommunityValidationError;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record CommunityCreateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        UUID riss,
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String name,
        @NotNull(message = ValidationError.REQUIRED)
        @Positive(message = ValidationError.POSITIVE)
        Integer territory,
        @NotNull(message = ValidationError.REQUIRED)
        @Pattern(regexp = "^[A-Za-z]$", message = CommunityValidationError.SECTOR_PATTERN)
        String sector,
        @NotNull(message = ValidationError.REQUIRED)
        @Positive(message = ValidationError.POSITIVE)
        Long population
) {
}
