package com.ddrissq.sigdosi.iam.permission.service;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionCreateRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionSearchRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionUpdateRequest;
import com.ddrissq.sigdosi.iam.permission.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.UUID;

public interface PermissionService {

    PermissionResponse get(UUID id);
    PermissionResponse create(PermissionCreateRequest request);
    PermissionResponse update(UUID id, PermissionUpdateRequest request);
    Page<PermissionResponse> getAll(PermissionSearchRequest request, Pageable pageable);
    Permission getByIdOrThrow(UUID id);
    Set<Permission> getAllById(Set<UUID> ids);

}
