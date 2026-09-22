package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserRoleUpdateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        UUID role
) {
}
