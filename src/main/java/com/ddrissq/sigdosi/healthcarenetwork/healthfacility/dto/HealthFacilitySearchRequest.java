package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto;

import com.ddrissq.sigdosi.common.validation.annotation.CompareFields;
import com.ddrissq.sigdosi.common.validation.annotation.ComparisonOperator;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacilityStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyTenure;

import java.math.BigDecimal;
import java.util.UUID;

@CompareFields(
        first = "minTotalLandArea",
        second = "maxTotalLandArea",
        operator = ComparisonOperator.LESS_THAN_OR_EQUAL)
@CompareFields(
        first = "minBuildingFootprint",
        second = "maxBuildingFootprint",
        operator = ComparisonOperator.LESS_THAN_OR_EQUAL)
@CompareFields(
        first = "minAvailableExpansionArea",
        second = "maxAvailableExpansionArea",
        operator = ComparisonOperator.LESS_THAN_OR_EQUAL)
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
