package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto;

import com.ddrissq.sigdosi.common.validation.annotation.AllowedGeometryTypes;
import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
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
        UUID type,
        Boolean isHeadquarters,
        HealthFacilityStatus status,
        @PositiveOrZero(message = ValidationError.POSITIVE_OR_ZERO)
        BigDecimal totalLandArea,
        @PositiveOrZero(message = ValidationError.POSITIVE_OR_ZERO)
        BigDecimal buildingFootprint,
        @PositiveOrZero(message = ValidationError.POSITIVE_OR_ZERO)
        BigDecimal availableExpansionArea,
        PropertyTenure propertyTenure,
        PropertyStatus propertyStatus,
        @NullableNotBlank
        String notes,
        @AllowedGeometryTypes(value = Point.class)
        Geometry location
) {
}
