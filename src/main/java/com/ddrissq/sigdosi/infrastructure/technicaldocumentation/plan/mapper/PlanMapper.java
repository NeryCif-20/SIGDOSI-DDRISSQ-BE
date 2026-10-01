package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanCreateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.model.Plan;
import org.mapstruct.*;

@Mapper
public interface PlanMapper {

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "healthFacility", ignore = true)
    @Mapping(target = "path", ignore = true)
    Plan toPlan(PlanCreateRequest request);

    PlanResponse toResponse(Plan plan);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "healthFacility", ignore = true)
    @Mapping(target = "path", ignore = true)
    void updatePlan(PlanUpdateRequest request, @MappingTarget Plan plan);

}
