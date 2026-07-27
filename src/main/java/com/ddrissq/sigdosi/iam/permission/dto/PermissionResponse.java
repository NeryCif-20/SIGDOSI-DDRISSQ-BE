package com.ddrissq.sigdosi.iam.permission.dto;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;

import java.util.UUID;

public record PermissionResponse(
        UUID id,
        String module,
        PermissionAction action
) {
}
