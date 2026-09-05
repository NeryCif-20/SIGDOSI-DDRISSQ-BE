package com.ddrissq.sigdosi.iam.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RoleCreateRequest(
        @NotBlank(message = "El nombre es obligatorio.")
        @Size(max = 30, message = "El tamaño maximo del nombre es de {max} caracteres")
        String name,
        String description,
        List<UUID> permissions
) {
}
