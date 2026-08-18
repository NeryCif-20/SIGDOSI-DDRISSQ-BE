package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.iam.user.model.UserAccountStatus;
import jakarta.validation.constraints.NotNull;

public record UserStatusUpdateRequest(
        @NotNull(message = "El estatus el obligatorio")
        UserAccountStatus status
) {
}
