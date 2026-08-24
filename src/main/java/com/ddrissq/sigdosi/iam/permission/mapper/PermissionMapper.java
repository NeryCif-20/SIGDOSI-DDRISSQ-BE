package com.ddrissq.sigdosi.iam.permission.mapper;

import com.ddrissq.sigdosi.common.mapper.util.StringMapper;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionCreateRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionUpdateRequest;
import com.ddrissq.sigdosi.iam.permission.model.Permission;
import org.mapstruct.*;

@Mapper(uses = StringMapper.class)
public interface PermissionMapper {

    @Mapping(target = "module", source = "module", qualifiedByName = "toUpperCase")
    Permission toPermission(PermissionCreateRequest request);

    PermissionResponse toResponse(Permission permission);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "module", source = "module", qualifiedByName = "toUpperCase")
    void updatePermission(PermissionUpdateRequest request, @MappingTarget Permission permission);

}
