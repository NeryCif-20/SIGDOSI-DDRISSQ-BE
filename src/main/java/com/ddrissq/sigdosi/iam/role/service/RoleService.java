package com.ddrissq.sigdosi.iam.role.service;

import com.ddrissq.sigdosi.iam.role.dto.RoleCreateRequest;
import com.ddrissq.sigdosi.iam.role.dto.RoleResponse;
import com.ddrissq.sigdosi.iam.role.dto.RoleSearchRequest;
import com.ddrissq.sigdosi.iam.role.dto.RoleUpdateRequest;
import com.ddrissq.sigdosi.iam.role.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RoleService {

    RoleResponse get(UUID id);
    RoleResponse create(RoleCreateRequest request);
    RoleResponse update(UUID id, RoleUpdateRequest request);
    Page<RoleResponse> getAll(Pageable pageable, RoleSearchRequest request);

    Role findById(UUID id);

}
