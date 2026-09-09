package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

import com.ddrissq.sigdosi.healthcarenetwork.community.constant.CommunityErrorMessageKeys;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record CommunityCreateRequest(
        @NotNull
        UUID riss,
        @NotBlank
        @Size(max = 50)
        String name,
        @NotNull
        @Positive
        Integer territory,
        @NotBlank
        @Pattern(regexp = "^[A-Za-z]$", message = CommunityErrorMessageKeys.SECTOR_PATTERN)
        String sector,
        @NotNull
        @Positive
        Long population
) {
}
