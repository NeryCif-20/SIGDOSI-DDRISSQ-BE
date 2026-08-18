package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.shared.validation.annotation.FieldMatch;
import jakarta.validation.constraints.NotBlank;

@FieldMatch(
        field = "newPassword",
        fieldMatch = "confirmNewPassword",
        message = "No coincide con la nueva contraseña")
public record UserPasswordUpdateRequest(
        @NotBlank(message = "La contraseña actual es obligatoria")
        String currentPassword,
        @NotBlank(message = "La nueva contraseña es obligatoria")
        String newPassword,
        @NotBlank(message = "Es obligatorio confirmar la nueva contraseña")
        String confirmNewPassword
) {
}
