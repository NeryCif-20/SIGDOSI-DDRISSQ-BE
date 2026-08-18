package com.ddrissq.sigdosi.iam.user.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserExceptionMessages {

    public static final String NOT_FOUND = "El usuario solicitado no fue encontrado";
    public static final String EMAIL_ALREADY_EXISTS = "Ya existe un usuario con el email especificado";
    public static final String CUI_ALREADY_EXISTS = "Ya existe un usuario con el cui especificado";
    public static final String PHONE_NUMBER_ALREADY_EXISTS = "Ya existe un usuario con el número de teléfono especificado";

}
