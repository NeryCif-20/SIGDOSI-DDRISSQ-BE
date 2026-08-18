package com.ddrissq.sigdosi.iam.permission.mapper;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionCreateRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionUpdateRequest;
import com.ddrissq.sigdosi.iam.permission.entity.Permission;
import org.mapstruct.*;

@Mapper
public interface PermissionMapper {

    Permission toPermission(PermissionCreateRequest request);

    PermissionResponse toResponse(Permission permission);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePermission(PermissionUpdateRequest request, @MappingTarget Permission permission);

}
