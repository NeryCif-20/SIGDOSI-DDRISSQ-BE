package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record CommunityUpdateRequest(
        UUID riss,
        String name,
        Integer territory,
        @Pattern(regexp = "^[A-Za-z]$", message = "El sector solo puede ser una letra")
        String sector,
        Long population
) {
}
