package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.mapper;

import com.ddrissq.sigdosi.common.util.mapper.StringFormatter;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialCreateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialResponse;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.model.BuildingMaterial;
import org.mapstruct.*;

@Mapper(uses = StringFormatter.class)
public interface BuildingMaterialMapper {

    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCase")
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    BuildingMaterial toBuildingMaterial(BuildingMaterialCreateRequest request);

    BuildingMaterialResponse toResponse(BuildingMaterial buildingMaterial);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",  ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCase")
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updateBuildingMaterial(BuildingMaterialUpdateRequest request, @MappingTarget BuildingMaterial buildingMaterial);

}
