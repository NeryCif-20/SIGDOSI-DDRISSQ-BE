package com.ddrissq.sigdosi.iam.permission.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.common.mapping.util.StringFunctions;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionCreateRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionUpdateRequest;
import com.ddrissq.sigdosi.iam.permission.model.Permission;
import org.mapstruct.*;

@Mapper(uses = StringFunctions.class)
public interface PermissionMapper {

    @Mapping(target = "module", source = "module", qualifiedByName = "toUpperCase")
    Permission toPermission(PermissionCreateRequest request);

    PermissionResponse toResponse(Permission permission);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "module", source = "module", qualifiedByName = "toUpperCase")
    void updatePermission(PermissionUpdateRequest request, @MappingTarget Permission permission);

}
