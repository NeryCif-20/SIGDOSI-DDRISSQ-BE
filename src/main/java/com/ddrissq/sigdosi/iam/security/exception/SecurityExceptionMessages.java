package com.ddrissq.sigdosi.iam.security.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityExceptionMessages {

    public static final String EXPIRED_TOKEN = "El token ha expirado";
    public static final String INVALID_TOKEN = "El token es inválido";
    public static final String AUTHENTICATION_REQUIRED = "Se necesita autenticación";
    public static final String ACCESS_DENIED = "No tiene permisos para acceder a este recurso";

}
