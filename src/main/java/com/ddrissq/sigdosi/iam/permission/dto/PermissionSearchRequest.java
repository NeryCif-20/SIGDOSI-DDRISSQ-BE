package com.ddrissq.sigdosi.iam.permission.dto;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import lombok.Builder;

@Builder
public record PermissionSearchRequest(
        String q,
        PermissionAction action
) {
}
