package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto;

import com.ddrissq.sigdosi.common.validation.geometrytype.annotation.GeometryType;
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
        @PositiveOrZero(message = "El area total debe ser mayor o igual a cero")
        BigDecimal totalLandArea,
        @PositiveOrZero(message = "El área ocupada debe ser mayor o igual a cero")
        BigDecimal buildingFootprint,
        @PositiveOrZero(message = "El área disponible debe ser mayor o igual a cero")
        BigDecimal availableExpansionArea,
        PropertyTenure propertyTenure,
        PropertyStatus propertyStatus,
        String notes,
        @GeometryType(expectedType = Point.class, message = "La ubicación debe ser un punto geográfico")
        Geometry location
) {
}
