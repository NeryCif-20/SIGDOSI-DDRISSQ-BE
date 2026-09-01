package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.mapper;

import com.ddrissq.sigdosi.common.mapper.util.StringMapper;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.model.HealthFacilityType;
import org.mapstruct.*;

@Mapper(uses = StringMapper.class)
public interface HealthFacilityTypeMapper {

    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCase")
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    HealthFacilityType toHealthFacilityType(HealthFacilityTypeCreateRequest request);

    HealthFacilityTypeResponse toResponse(HealthFacilityType healthFacilityType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCase")
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updateHealthFacilityType(HealthFacilityTypeUpdateRequest request, @MappingTarget HealthFacilityType healthFacilityType);

}
