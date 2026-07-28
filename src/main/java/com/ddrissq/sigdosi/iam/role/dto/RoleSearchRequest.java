package com.ddrissq.sigdosi.iam.role.dto;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;

public record RoleSearchRequest(
        String name,
        String module,
        PermissionAction action
) {
}
