package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.common.mapping.util.StringFunctions;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.model.HealthFacilityType;
import org.mapstruct.*;

@Mapper(uses = StringFunctions.class)
public interface HealthFacilityTypeMapper {

    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCase")
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    HealthFacilityType toHealthFacilityType(HealthFacilityTypeCreateRequest request);

    HealthFacilityTypeResponse toResponse(HealthFacilityType healthFacilityType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCase")
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updateHealthFacilityType(HealthFacilityTypeUpdateRequest request, @MappingTarget HealthFacilityType healthFacilityType);

}
