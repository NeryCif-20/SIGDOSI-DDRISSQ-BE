package com.ddrissq.sigdosi.iam.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RoleCreateRequest(
        @NotBlank
        @Size(max = 30)
        String name,
        String description,
        List<UUID> permissions
) {
}
