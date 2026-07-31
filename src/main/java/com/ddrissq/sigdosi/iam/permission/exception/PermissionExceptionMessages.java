package com.ddrissq.sigdosi.iam.permission.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PermissionExceptionMessages {

    public static final String NOT_FOUND = "El permiso solicitado no fue encontrado";
    public static final String ALREADY_EXISTS = "Ya existe un permiso con el modulo y acción especificados.";

}
