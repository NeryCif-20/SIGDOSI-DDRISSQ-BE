package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.mapper;

import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.mapper.BuildingElementMapper;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.mapper.BuildingMaterialMapper;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.model.FacilityElement;
import org.mapstruct.Mapper;

@Mapper(uses = {BuildingElementMapper.class, BuildingMaterialMapper.class})
public interface FacilityElementMapper {

    FacilityElementResponse toResponse(FacilityElement facilityElement);

}
