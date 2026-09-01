package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.mapper;

import com.ddrissq.sigdosi.common.mapper.util.StringMapper;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeCreateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.model.PlanType;
import org.mapstruct.*;

@Mapper(uses = StringMapper.class)
public interface PlanTypeMapper {

    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCase")
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    PlanType toPlanType(PlanTypeCreateRequest request);

    PlanTypeResponse toResponse(PlanType planType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCase")
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updatePlanType(PlanTypeUpdateRequest request, @MappingTarget PlanType planType);

}
