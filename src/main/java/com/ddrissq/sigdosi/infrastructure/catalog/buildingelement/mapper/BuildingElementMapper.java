package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.common.mapping.util.StringFunctions;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementCreateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementResponse;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.model.BuildingElement;
import org.mapstruct.*;

@Mapper(uses = StringFunctions.class)
public interface BuildingElementMapper {

    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    BuildingElement toBuildingElement(BuildingElementCreateRequest request);

    BuildingElementResponse toResponse(BuildingElement buildingElement);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updateBuildingElement(BuildingElementUpdateRequest request, @MappingTarget BuildingElement buildingElement);

}
