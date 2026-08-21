package com.ddrissq.sigdosi.iam.role.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final  class RoleErrorMessages {

    public static final String NOT_FOUND = "El rol solicitado no fue encontrado";
    public static final String ALREADY_EXISTS = "Ya existe un rol con el nombre especificado";

}
