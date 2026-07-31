package com.ddrissq.sigdosi.iam.permission.dto;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import lombok.Builder;

@Builder
public record PermissionUpdateRequest(
        String module,
        PermissionAction action
) {
}
