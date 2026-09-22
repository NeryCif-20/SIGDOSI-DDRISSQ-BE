package com.ddrissq.sigdosi.healthcarenetwork.dms.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.common.mapping.util.StringFunctions;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsResponse;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.model.Dms;
import org.mapstruct.*;

@Mapper(uses = StringFunctions.class)
public interface DmsMapper {

    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    Dms toDms(DmsCreateRequest request);

    DmsResponse toResponse(Dms dms);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updateDms(DmsUpdateRequest request, @MappingTarget Dms dms);

}
