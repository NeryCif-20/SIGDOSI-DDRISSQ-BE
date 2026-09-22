package com.ddrissq.sigdosi.healthcarenetwork.riss.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.common.mapping.util.StringFunctions;
import com.ddrissq.sigdosi.healthcarenetwork.dms.mapper.DmsMapper;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissResponse;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.riss.model.Riss;
import org.mapstruct.*;

@Mapper(uses = { DmsMapper.class, StringFunctions.class })
public interface RissMapper {

    @Mapping(target = "dms", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    Riss toRiss(RissCreateRequest request);

    RissResponse toResponse(Riss riss);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "dms", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updateRiss(RissUpdateRequest request, @MappingTarget Riss riss);

}
