package com.ddrissq.sigdosi.iam.permission.dto;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PermissionUpdateRequest(
        @Size(max = 20)
        String module,
        PermissionAction action
) {
}
