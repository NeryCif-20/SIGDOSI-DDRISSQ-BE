package com.ddrissq.sigdosi.iam.auth.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthErrorMessages {

    public static final String USER_DISABLED = "No es posible iniciar sesión en este momento";
    public static final String BAD_CREDENTIALS = "Las credenciales son incorrectas";

}
