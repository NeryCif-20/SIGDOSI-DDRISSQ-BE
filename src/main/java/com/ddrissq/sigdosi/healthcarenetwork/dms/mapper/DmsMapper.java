package com.ddrissq.sigdosi.healthcarenetwork.dms.mapper;

import com.ddrissq.sigdosi.common.util.mapper.StringFormatter;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsResponse;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.model.Dms;
import org.mapstruct.*;

@Mapper(uses = StringFormatter.class)
public interface DmsMapper {

    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    Dms toDms(DmsCreateRequest request);

    DmsResponse toResponse(Dms dms);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updateDms(DmsUpdateRequest request, @MappingTarget Dms dms);

}
