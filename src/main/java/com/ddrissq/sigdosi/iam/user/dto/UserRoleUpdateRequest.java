package com.ddrissq.sigdosi.iam.user.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserRoleUpdateRequest(
        @NotNull
        UUID role
) {
}
