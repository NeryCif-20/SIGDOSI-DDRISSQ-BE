package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.common.mapping.util.GeometryFunctions;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto.HealthFacilityImageResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.model.HealthFacilityImage;
import org.mapstruct.*;

@Mapper(uses = GeometryFunctions.class)
public interface HealthFacilityMapper {

    @Mapping(target = "community", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "location", source = "location", qualifiedByName = "toPoint")
    HealthFacility toHealthFacility(HealthFacilityCreateRequest request);

    HealthFacilityResponse toResponse(HealthFacility healthFacility);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "community", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "location", source = "location", qualifiedByName = "toPoint")
    void updateHealthFacility(HealthFacilityUpdateRequest request, @MappingTarget HealthFacility healthFacility);

    HealthFacilityImageResponse toResponse(HealthFacilityImage healthFacilityImage);

}
