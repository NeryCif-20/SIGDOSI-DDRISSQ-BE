package com.ddrissq.sigdosi.iam.permission.dto;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PermissionResponse(
        UUID id,
        String module,
        PermissionAction action
) {
}
