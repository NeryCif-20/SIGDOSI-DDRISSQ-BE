package com.ddrissq.sigdosi.iam.role.dto;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record RoleResponse(
        UUID id,
        String name,
        String description,
        Set<PermissionResponse> permissions
) {
}
