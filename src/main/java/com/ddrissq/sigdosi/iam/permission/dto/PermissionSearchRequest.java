package com.ddrissq.sigdosi.iam.permission.dto;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;

public record PermissionSearchRequest(
        String module,
        PermissionAction action
) {
}
