package com.ddrissq.sigdosi.iam.role.dto;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import lombok.Builder;

@Builder
public record RoleSearchRequest(
        String q,
        PermissionAction action
) {
}
