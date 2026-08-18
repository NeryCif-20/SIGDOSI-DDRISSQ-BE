package com.ddrissq.sigdosi.iam.user.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserRoleUpdateRequest(
        @NotNull(message = "El rol es obligatorio")
        UUID role
) {
}
