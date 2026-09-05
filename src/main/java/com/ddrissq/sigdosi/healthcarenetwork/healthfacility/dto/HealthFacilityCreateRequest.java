package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto;

import com.ddrissq.sigdosi.common.validation.annotation.GeometryType;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacilityStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyTenure;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.util.UUID;

public record HealthFacilityCreateRequest(
        @NotNull(message = "La comunidad es obligatorio")
        UUID community,
        @NotNull(message = "El tipo de establecimeinto es obligatorio")
        UUID healthFacilityType,
        @NotNull(message = "Especificar si es sede es obligatorio")
        Boolean isHeadquarters,
        @NotNull(message = "El estado es obligatorio")
        HealthFacilityStatus status,
        @NotNull(message = "El área total es obligatoria")
        @PositiveOrZero(message = "El area total debe ser mayor o igual a cero")
        BigDecimal totalLandArea,
        @NotNull(message = "El área ocupada es obligatoria")
        @PositiveOrZero(message = "El área ocupada debe ser mayor o igual a cero")
        BigDecimal buildingFootprint,
        @NotNull(message = "El área disponible es obligatoria")
        @PositiveOrZero(message = "El área disponible debe ser mayor o igual a cero")
        BigDecimal availableExpansionArea,
        @NotNull(message = "La tenencia de la propiedad es obligatoria")
        PropertyTenure propertyTenure,
        @NotNull(message = "El estado de la propiedad es obligatorio")
        PropertyStatus propertyStatus,
        String notes,
        @GeometryType(expectedType = Point.class, message = "La ubicación debe ser un punto geográfico")
        Geometry location
) {
}
