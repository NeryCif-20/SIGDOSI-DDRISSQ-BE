package com.ddrissq.sigdosi.infrastructure.request.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.common.mapping.util.StringFunctions;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestResponse;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.request.model.Request;
import org.mapstruct.*;

@Mapper(uses = StringFunctions.class)
public interface RequestMapper {

    @Mapping(target = "healthFacility", ignore = true)
    @Mapping(target = "referenceCode", ignore = true)
    @Mapping(target = "requesterName", source = "requesterName", qualifiedByName = "capitalize")
    @Mapping(target = "requesterEmail", source = "requesterEmail", qualifiedByName = "toLowerCase")
    @Mapping(target = "requesterOfficialPosition", source = "requesterOfficialPosition", qualifiedByName = "toUpperCase")
    @Mapping(target = "requesterFunctionalPosition", source = "requesterFunctionalPosition", qualifiedByName = "toUpperCase")
    Request toRequest(RequestCreateRequest request);

    RequestResponse toResponse(Request request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "healthFacility", ignore = true)
    @Mapping(target = "referenceCode", ignore = true)
    @Mapping(target = "requesterName", source = "requesterName", qualifiedByName = "capitalize")
    @Mapping(target = "requesterEmail", source = "requesterEmail", qualifiedByName = "toLowerCase")
    @Mapping(target = "requesterOfficialPosition", source = "requesterOfficialPosition", qualifiedByName = "toUpperCase")
    @Mapping(target = "requesterFunctionalPosition", source = "requesterFunctionalPosition", qualifiedByName = "toUpperCase")
    void updateRequest(RequestUpdateRequest request, @MappingTarget Request updateRequest);


}
