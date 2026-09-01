package com.ddrissq.sigdosi.iam.role.mapper;

import com.ddrissq.sigdosi.common.mapper.util.StringMapper;
import com.ddrissq.sigdosi.iam.permission.mapper.PermissionMapper;
import com.ddrissq.sigdosi.iam.role.dto.RoleCreateRequest;
import com.ddrissq.sigdosi.iam.role.dto.RoleResponse;
import com.ddrissq.sigdosi.iam.role.dto.RoleUpdateRequest;
import com.ddrissq.sigdosi.iam.role.model.Role;
import org.mapstruct.*;

@Mapper(uses = {PermissionMapper.class, StringMapper.class})
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    Role toRole(RoleCreateRequest request);

    RoleResponse toResponse(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "capitalize")
    void updateRole(RoleUpdateRequest request, @MappingTarget Role role);

}
