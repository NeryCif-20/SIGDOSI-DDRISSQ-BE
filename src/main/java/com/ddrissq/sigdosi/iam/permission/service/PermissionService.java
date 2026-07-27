package com.ddrissq.sigdosi.iam.permission.service;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionCreateRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionSearchRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionUpdateRequest;
import com.ddrissq.sigdosi.iam.permission.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

    PermissionResponse get(UUID id);
    PermissionResponse create(PermissionCreateRequest request);
    PermissionResponse update(UUID id, PermissionUpdateRequest request);
    void delete(UUID id);
    Page<PermissionResponse> getAll(Pageable pageable, PermissionSearchRequest request);

    Permission findById(UUID id);

}
