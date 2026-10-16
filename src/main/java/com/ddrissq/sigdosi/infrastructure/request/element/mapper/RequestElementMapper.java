package com.ddrissq.sigdosi.infrastructure.request.element.mapper;

import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.mapper.BuildingElementMapper;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.mapper.BuildingMaterialMapper;
import com.ddrissq.sigdosi.infrastructure.request.element.dto.RequestElementResponse;
import com.ddrissq.sigdosi.infrastructure.request.element.model.RequestElement;
import org.mapstruct.Mapper;

@Mapper(uses = { BuildingElementMapper.class, BuildingMaterialMapper.class })
public interface RequestElementMapper {

    RequestElementResponse toResponse(RequestElement requestElement);

}
