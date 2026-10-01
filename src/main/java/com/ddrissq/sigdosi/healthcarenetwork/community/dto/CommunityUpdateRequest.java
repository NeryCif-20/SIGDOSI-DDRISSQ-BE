package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.healthcarenetwork.community.error.CommunityValidationError;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CommunityUpdateRequest(
        UUID riss,
        @NullableNotBlank
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String name,
        Short territory,
        @Pattern(regexp = "^[A-Za-z]$", message = CommunityValidationError.SECTOR_PATTERN)
        String sector,
        Long population
) {
}
