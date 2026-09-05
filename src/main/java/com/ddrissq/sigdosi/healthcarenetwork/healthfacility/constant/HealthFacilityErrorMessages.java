package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HealthFacilityErrorMessages {

    public static final String NOT_FOUND = "El establecimiento de salud solicitado no fue encontrado";
    public static final String TOTAL_LAND_AREA_EXCEEDED = "El área estimada del terreno no puede superar el área total del terreno";
}
