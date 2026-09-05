package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto;

import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacilityStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyTenure;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeResponse;
import org.locationtech.jts.geom.Geometry;

import java.math.BigDecimal;
import java.util.UUID;

public record HealthFacilityResponse(
        UUID id,
        CommunityResponse community,
        HealthFacilityTypeResponse healthFacilityType,
        Boolean isHeadquarters,
        HealthFacilityStatus status,
        BigDecimal totalLandArea,
        BigDecimal buildingFootprint,
        BigDecimal availableExpansionArea,
        PropertyTenure propertyTenure,
        PropertyStatus propertyStatus,
        String notes,
        Geometry location
) {
}
