package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import org.mapstruct.*;

@Mapper
public interface HealthFacilityMapper {

    @Mapping(target = "community", ignore = true)
    @Mapping(target = "healthFacilityType", ignore = true)
    HealthFacility toHealthFacility(HealthFacilityCreateRequest request);

    HealthFacilityResponse toResponse(HealthFacility healthFacility);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "community", ignore = true)
    @Mapping(target = "healthFacilityType", ignore = true)
    void updateHealthFacility(HealthFacilityUpdateRequest request, @MappingTarget HealthFacility healthFacility);

}
