package com.ddrissq.sigdosi.iam.role.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.common.mapping.util.StringFunctions;
import com.ddrissq.sigdosi.iam.permission.mapper.PermissionMapper;
import com.ddrissq.sigdosi.iam.role.dto.RoleCreateRequest;
import com.ddrissq.sigdosi.iam.role.dto.RoleResponse;
import com.ddrissq.sigdosi.iam.role.dto.RoleUpdateRequest;
import com.ddrissq.sigdosi.iam.role.model.Role;
import org.mapstruct.*;

@Mapper(uses = {PermissionMapper.class, StringFunctions.class})
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    Role toRole(RoleCreateRequest request);

    RoleResponse toResponse(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updateRole(RoleUpdateRequest request, @MappingTarget Role role);

}
