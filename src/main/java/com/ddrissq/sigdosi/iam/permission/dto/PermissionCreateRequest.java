package com.ddrissq.sigdosi.iam.permission.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PermissionCreateRequest(
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 20, message = ValidationError.SIZE)
        String module,
        @NotNull(message = ValidationError.REQUIRED)
        PermissionAction action
) {
}
