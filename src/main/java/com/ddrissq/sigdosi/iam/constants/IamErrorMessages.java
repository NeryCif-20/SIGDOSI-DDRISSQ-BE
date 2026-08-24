package com.ddrissq.sigdosi.iam.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IamErrorMessages {

    public static final String USER_DISABLED = "No es posible iniciar sesión en este momento";
    public static final String BAD_CREDENTIALS = "Las credenciales son incorrectas";
    public static final String EXPIRED_TOKEN = "El token ha expirado";
    public static final String INVALID_TOKEN = "El token es inválido";
    public static final String AUTHENTICATION_REQUIRED = "Se necesita autenticación";
    public static final String ACCESS_DENIED = "No tiene permisos para acceder a este recurso";

}
