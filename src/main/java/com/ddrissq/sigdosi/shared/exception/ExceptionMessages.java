package com.ddrissq.sigdosi.shared.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessages {

    public static final String VALIDATION_FAILED = "Se ha producido uno o mas errores de validacion";
    public static final String INVALID_REQUEST_BODY = "El cuerpo de la solicitud no existe o sus datos son incorrectos";
    public static final String METHOD_NOT_ALLOWED = "El metodo de la solicitud no esta permitido";

    public static String invalidParameter(String parameter) {
        return "El parametro '" + parameter + "' es invalido";
    }

}
