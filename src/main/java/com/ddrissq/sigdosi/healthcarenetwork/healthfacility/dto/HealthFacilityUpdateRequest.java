package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto;

import com.ddrissq.sigdosi.common.validation.geometry.annotation.GeometryType;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.constant.HealthFacilityErrorMessageKeys;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacilityStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyTenure;
import jakarta.validation.constraints.PositiveOrZero;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.util.UUID;

public record HealthFacilityUpdateRequest(
        UUID community,
        UUID healthFacilityType,
        Boolean isHeadquarters,
        HealthFacilityStatus status,
        @PositiveOrZero
        BigDecimal totalLandArea,
        @PositiveOrZero
        BigDecimal buildingFootprint,
        @PositiveOrZero
        BigDecimal availableExpansionArea,
        PropertyTenure propertyTenure,
        PropertyStatus propertyStatus,
        String notes,
        @GeometryType(
                expectedType = Point.class,
                message = HealthFacilityErrorMessageKeys.LOCATION_INVALID)
        Geometry location
) {
}
