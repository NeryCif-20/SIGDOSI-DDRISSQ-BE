package com.ddrissq.sigdosi.iam.role.dto;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;

import java.util.Set;
import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        String description,
        Set<PermissionResponse> permissions
) {
}
