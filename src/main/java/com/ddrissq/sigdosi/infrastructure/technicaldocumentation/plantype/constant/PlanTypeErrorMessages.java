package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlanTypeErrorMessages {

    public static final String NOT_FOUND = "El tipo de plano no existe";
    public static final String ALREADY_EXISTS = "Ya existe un tipo de plano con el codigo especificado";

}
