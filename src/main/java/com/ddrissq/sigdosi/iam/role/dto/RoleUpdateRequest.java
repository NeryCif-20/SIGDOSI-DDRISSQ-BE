package com.ddrissq.sigdosi.iam.role.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record RoleUpdateRequest(
        @Size(max = 30, message = "El tamaño maximo del nombre es de {max} caracteres")
        String name,
        String description,
        Set<UUID> permissions
) {
}
