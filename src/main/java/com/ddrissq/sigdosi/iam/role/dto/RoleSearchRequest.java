package com.ddrissq.sigdosi.iam.role.dto;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record RoleSearchRequest(
        String search,
        Set<UUID> permissions
) {
}
