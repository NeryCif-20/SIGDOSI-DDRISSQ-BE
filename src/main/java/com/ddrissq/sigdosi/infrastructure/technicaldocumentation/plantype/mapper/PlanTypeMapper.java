package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.common.mapping.util.StringFunctions;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeCreateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.model.PlanType;
import org.mapstruct.*;

@Mapper(uses = StringFunctions.class)
public interface PlanTypeMapper {

    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCase")
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    PlanType toPlanType(PlanTypeCreateRequest request);

    PlanTypeResponse toResponse(PlanType planType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCase")
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updatePlanType(PlanTypeUpdateRequest request, @MappingTarget PlanType planType);

}
