package com.ddrissq.sigdosi.healthcarenetwork.riss.mapper;

import com.ddrissq.sigdosi.common.util.mapper.StringFormatter;
import com.ddrissq.sigdosi.healthcarenetwork.dms.mapper.DmsMapper;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissResponse;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.riss.model.Riss;
import org.mapstruct.*;

@Mapper(uses = { DmsMapper.class, StringFormatter.class })
public interface RissMapper {

    @Mapping(target = "dms", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    Riss toRiss(RissCreateRequest request);

    RissResponse toResponse(Riss riss);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "dms", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updateRiss(RissUpdateRequest request, @MappingTarget Riss riss);

}
