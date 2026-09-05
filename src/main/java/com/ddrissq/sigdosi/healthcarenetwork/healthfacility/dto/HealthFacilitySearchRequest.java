package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto;

import com.ddrissq.sigdosi.common.validation.annotation.RangeOrder;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacilityStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyTenure;

import java.math.BigDecimal;
import java.util.UUID;

@RangeOrder(
        minField = "minTotalLandArea",
        maxField = "maxTotalLandArea",
        message = "El área mínima total del terreno no puede ser mayor que el área máxima total del terreno")
@RangeOrder(
        minField = "minBuildingFootprint",
        maxField = "maxBuildingFootprint",
        message = "El área mínima de construcción no puede ser mayor que el área máxima de construcción")
@RangeOrder(
        minField = "minAvailableExpansionArea",
        maxField = "maxAvailableExpansionArea",
        message = "El área mínima disponible para expansión no puede ser mayor que el área máxima disponible para expansión")
public record HealthFacilitySearchRequest(
        UUID community,
        UUID healthFacilityType,
        Boolean isHeadquarters,
        HealthFacilityStatus status,
        BigDecimal minTotalLandArea,
        BigDecimal maxTotalLandArea,
        BigDecimal minBuildingFootprint,
        BigDecimal maxBuildingFootprint,
        BigDecimal minAvailableExpansionArea,
        BigDecimal maxAvailableExpansionArea,
        PropertyTenure propertyTenure,
        PropertyStatus propertyStatus
) {
}
