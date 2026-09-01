package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.mapper;

import com.ddrissq.sigdosi.common.mapper.util.StringMapper;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementCreateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementResponse;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.model.BuildingElement;
import org.mapstruct.*;

@Mapper(uses = StringMapper.class)
public interface BuildingElementMapper {

    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    BuildingElement toBuildingElement(BuildingElementCreateRequest request);

    BuildingElementResponse toResponse(BuildingElement buildingElement);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updateBuildingElement(BuildingElementUpdateRequest request, @MappingTarget BuildingElement buildingElement);

}
