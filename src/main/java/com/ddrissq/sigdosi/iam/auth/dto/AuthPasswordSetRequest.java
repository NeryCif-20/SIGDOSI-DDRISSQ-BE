package com.ddrissq.sigdosi.iam.auth.dto;

import com.ddrissq.sigdosi.common.validation.compare.annotation.Compare;
import jakarta.validation.constraints.NotBlank;

@Compare(
        firstField = "confirmNewPassword",
        secondField = "newPassword",
        message = "Debe coincidir con la nueva contraseña")
public record AuthPasswordSetRequest(
        @NotBlank(message = "El token es obligatorio")
        String token,
        @NotBlank(message = "La nueva contraseña es obligatoria")
        String newPassword,
        @NotBlank(message = "Es obligatorio confirmar la nueva contraseña")
        String confirmNewPassword
) {
}
