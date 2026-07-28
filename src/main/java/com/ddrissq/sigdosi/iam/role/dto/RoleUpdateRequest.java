package com.ddrissq.sigdosi.iam.role.dto;

import java.util.Set;
import java.util.UUID;

public record RoleUpdateRequest(
        String name,
        String description,
        Set<UUID> permissions
) {
}
