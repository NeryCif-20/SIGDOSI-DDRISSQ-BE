package com.ddrissq.sigdosi.iam.permission.dto;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PermissionCreateRequest(
        @NotBlank(message = "El modulo es obligatorio.")
        @Size(max = 20, message = "El tamaño maximo del modulo es de {max} caracteres")
        String module,
        @NotNull(message = "La acción es obligatoria.")
        PermissionAction action
) {
}
