package com.ddrissq.sigdosi.iam.permission.dto;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;

public record PermissionUpdateRequest(
        String module,
        PermissionAction action
) {
}
