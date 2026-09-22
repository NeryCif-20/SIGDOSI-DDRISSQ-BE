package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto;

import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacilityStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyTenure;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.util.UUID;

public record HealthFacilityCreateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        UUID community,
        @NotNull(message = ValidationError.REQUIRED)
        UUID healthFacilityType,
        @NotNull(message = ValidationError.REQUIRED)
        Boolean isHeadquarters,
        @NotNull(message = ValidationError.REQUIRED)
        HealthFacilityStatus status,
        @NotNull(message = ValidationError.REQUIRED)
        @PositiveOrZero(message = ValidationError.POSITIVE_OR_ZERO)
        BigDecimal totalLandArea,
        @NotNull(message = ValidationError.REQUIRED)
        @PositiveOrZero(message = ValidationError.POSITIVE_OR_ZERO)
        BigDecimal buildingFootprint,
        @NotNull(message = ValidationError.REQUIRED)
        @PositiveOrZero(message = ValidationError.POSITIVE_OR_ZERO)
        BigDecimal availableExpansionArea,
        @NotNull(message = ValidationError.REQUIRED)
        PropertyTenure propertyTenure,
        @NotNull(message = ValidationError.REQUIRED)
        PropertyStatus propertyStatus,
        @NullableNotBlank
        String notes,
        Point location
) {
}
