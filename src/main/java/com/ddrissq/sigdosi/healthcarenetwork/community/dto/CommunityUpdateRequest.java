package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

import com.ddrissq.sigdosi.healthcarenetwork.community.constant.CommunityErrorMessageKeys;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CommunityUpdateRequest(
        UUID riss,
        @Size(max = 50)
        String name,
        Integer territory,
        @Pattern(regexp = "^[A-Za-z]$", message = CommunityErrorMessageKeys.SECTOR_PATTERN)
        String sector,
        Long population
) {
}
