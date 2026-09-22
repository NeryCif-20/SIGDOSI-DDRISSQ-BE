package com.ddrissq.sigdosi.iam.permission.dto;

import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PermissionUpdateRequest(
        @NullableNotBlank
        @Size(min = 1, max = 20, message = ValidationError.SIZE)
        String module,
        PermissionAction action
) {
}
