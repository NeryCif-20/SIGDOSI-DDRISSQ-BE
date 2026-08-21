package com.ddrissq.sigdosi.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {

    public static final String VALIDATION_FAILED = "Se ha producido uno o mas errores de validacion";
    public static final String INVALID_REQUEST_BODY = "El cuerpo de la solicitud no existe o sus datos son incorrectos";
    public static final String METHOD_NOT_ALLOWED = "El metodo de la solicitud no esta permitido";
    public static final String INVALID_PARAMETER = "El parametro envidado es invalido";
    public static final String INVALID_STATE_TRANSITION = "No se permite cambiar el estado de %s a %s";

}
