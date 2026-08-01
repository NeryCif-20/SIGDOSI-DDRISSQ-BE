package com.ddrissq.sigdosi.iam.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record RoleCreateRequest(
        @NotBlank(message = "El nombre es obligatorio.")
        String name,
        String description,
        Set<UUID> permissions
) {
}
