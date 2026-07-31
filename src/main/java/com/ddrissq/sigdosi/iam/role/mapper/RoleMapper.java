package com.ddrissq.sigdosi.iam.role.mapper;

import com.ddrissq.sigdosi.iam.permission.mapper.PermissionMapper;
import com.ddrissq.sigdosi.iam.role.dto.RoleCreateRequest;
import com.ddrissq.sigdosi.iam.role.dto.RoleResponse;
import com.ddrissq.sigdosi.iam.role.dto.RoleUpdateRequest;
import com.ddrissq.sigdosi.iam.role.model.Role;
import org.mapstruct.*;

@Mapper(uses = {PermissionMapper.class})
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleCreateRequest request);

    RoleResponse toResponse(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "permissions", ignore = true)
    void updateRole(RoleUpdateRequest request, @MappingTarget Role role);

}
