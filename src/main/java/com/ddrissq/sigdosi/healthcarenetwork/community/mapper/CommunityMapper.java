package com.ddrissq.sigdosi.healthcarenetwork.community.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.common.mapping.util.StringFunctions;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityResponse;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.community.model.Community;
import com.ddrissq.sigdosi.healthcarenetwork.riss.mapper.RissMapper;
import org.mapstruct.*;

@Mapper(uses = {RissMapper.class, StringFunctions.class})
public interface CommunityMapper {

    @Mapping(target = "riss", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    @Mapping(target = "sector", source = "sector", qualifiedByName = "toUpperCase")
    Community toCommunity(CommunityCreateRequest request);

    CommunityResponse toResponse(Community community);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "riss", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    @Mapping(target = "sector", source = "sector", qualifiedByName = "toUpperCase")
    void updateCommunity(CommunityUpdateRequest request, @MappingTarget Community community);

}
