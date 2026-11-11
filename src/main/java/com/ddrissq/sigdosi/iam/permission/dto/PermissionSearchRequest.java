package com.ddrissq.sigdosi.iam.permission.dto;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import lombok.Builder;

import java.util.Set;

@Builder
public record PermissionSearchRequest(
        String search,
        Set<PermissionAction> actions
) {
}
