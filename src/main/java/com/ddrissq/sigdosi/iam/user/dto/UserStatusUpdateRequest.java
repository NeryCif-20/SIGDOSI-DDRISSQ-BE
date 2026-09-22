package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.iam.user.model.UserStatus;
import jakarta.validation.constraints.NotNull;

public record UserStatusUpdateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        UserStatus status
) {
}
