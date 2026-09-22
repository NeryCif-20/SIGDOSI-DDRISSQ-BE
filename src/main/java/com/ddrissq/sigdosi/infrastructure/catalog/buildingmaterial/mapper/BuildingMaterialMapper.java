package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.common.mapping.util.StringFunctions;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialCreateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialResponse;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.model.BuildingMaterial;
import org.mapstruct.*;

@Mapper(uses = StringFunctions.class)
public interface BuildingMaterialMapper {

    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCase")
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    BuildingMaterial toBuildingMaterial(BuildingMaterialCreateRequest request);

    BuildingMaterialResponse toResponse(BuildingMaterial buildingMaterial);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "code", source = "code", qualifiedByName = "toUpperCase")
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updateBuildingMaterial(BuildingMaterialUpdateRequest request, @MappingTarget BuildingMaterial buildingMaterial);

}
