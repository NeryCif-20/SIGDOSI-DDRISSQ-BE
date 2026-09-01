package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BuildingMaterialErrorMessages {

    public static final String NOT_FOUND = "El material solicitado no fue encontrado";
    public static final String ALREADY_EXISTS = "Ya existe un material con el codigo especificado";

}
