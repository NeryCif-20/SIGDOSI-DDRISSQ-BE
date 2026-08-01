package com.ddrissq.sigdosi.iam.role.dto;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record RoleUpdateRequest(
        String name,
        String description,
        Set<UUID> permissions
) {
}
