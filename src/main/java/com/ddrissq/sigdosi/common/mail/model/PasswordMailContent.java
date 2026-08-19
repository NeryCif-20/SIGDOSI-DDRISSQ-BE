package com.ddrissq.sigdosi.common.mail.model;

import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenPurpose;

public record PasswordMailContent(
        String subject,
        String title,
        String actionText,
        String buttonText,
        String path
) {
    public static PasswordMailContent from(PasswordTokenPurpose purpose) {
        return switch (purpose) {
            case SETUP_PASSWORD -> new PasswordMailContent(
                    "Establece la contraseña de tu cuenta",
                    "Establece tu contraseña",
                    "establecer",
                    "Establecer contraseña",
                    "/auth/set-up-password"
            );
            case RESET_PASSWORD -> new PasswordMailContent(
                    "Restablece la contraseña de tu cuenta",
                    "Restablece tu contraseña",
                    "restablecer",
                    "Restablecer contraseña",
                    "/auth/reset-password"
            );
        };
    }
}
