package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.mapper;

import com.ddrissq.sigdosi.common.mapper.util.GeometryTransformer;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import org.mapstruct.*;

@Mapper(uses = GeometryTransformer.class)
public interface HealthFacilityMapper {

    @Mapping(target = "community", ignore = true)
    @Mapping(target = "healthFacilityType", ignore = true)
    @Mapping(target = "location", source = "location", qualifiedByName = "toPoint")
    HealthFacility toHealthFacility(HealthFacilityCreateRequest request);

    HealthFacilityResponse toResponse(HealthFacility healthFacility);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "community", ignore = true)
    @Mapping(target = "healthFacilityType", ignore = true)
    @Mapping(target = "location", source = "location", qualifiedByName = "toPoint")
    void updateHealthFacility(HealthFacilityUpdateRequest request, @MappingTarget HealthFacility healthFacility);

}
