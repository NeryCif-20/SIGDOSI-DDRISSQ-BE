package com.ddrissq.sigdosi.iam.role.service;

import com.ddrissq.sigdosi.iam.permission.model.Permission;
import com.ddrissq.sigdosi.iam.permission.service.PermissionService;
import com.ddrissq.sigdosi.iam.role.dto.RoleCreateRequest;
import com.ddrissq.sigdosi.iam.role.dto.RoleResponse;
import com.ddrissq.sigdosi.iam.role.dto.RoleSearchRequest;
import com.ddrissq.sigdosi.iam.role.dto.RoleUpdateRequest;
import com.ddrissq.sigdosi.iam.role.exception.RoleExceptionMessages;
import com.ddrissq.sigdosi.iam.role.mapper.RoleMapper;
import com.ddrissq.sigdosi.iam.role.model.Role;
import com.ddrissq.sigdosi.iam.role.repository.RoleRepository;
import com.ddrissq.sigdosi.iam.role.specification.RoleSpecification;
import com.ddrissq.sigdosi.shared.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.shared.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleRepository repository;
    private final RoleMapper mapper;
    private final PermissionService permissionService;

    @Override
    public RoleResponse get(UUID id) {
        Role role = findById(id);
        return mapper.toResponse(role);
    }

    @Override
    public RoleResponse create(RoleCreateRequest request) {
        validateUniqueName(request.name());
        Role role = mapper.toRole(request);
        updatePermissions(request.permissions(), role);
        Role savedRole = repository.save(role);
        return mapper.toResponse(savedRole);
    }

    @Override
    public RoleResponse update(UUID id, RoleUpdateRequest request) {
        Role role = findById(id);
        validateUniqueName(request.name(), id);
        mapper.updateRole(request, role);
        updatePermissions(request.permissions(), role);
        return mapper.toResponse(role);
    }

    @Override
    public Page<RoleResponse> getAll(Pageable pageable, RoleSearchRequest request) {
        Specification<Role> spec = Specification.allOf(
                RoleSpecification.hasName(request.name()),
                RoleSpecification.hasModule(request.module()),
                RoleSpecification.hasAction(request.action()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Role findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        RoleExceptionMessages.NOT_FOUND));
    }

    private void validateUniqueName(String name) {
        validateUniqueName(name, null);
    }

    private void validateUniqueName(String name, UUID id) {
        if (name == null) return;
        boolean exists = id == null
                ? repository.existsByName(name)
                : repository.existsByNameAndIdNot(name, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    RoleExceptionMessages.ALREADY_EXISTS);
        }
    }

    private void updatePermissions(Set<UUID> ids, Role role) {
        Set<Permission> permissions = new HashSet<>();
        if (ids != null && !ids.isEmpty()) {
            permissions = permissionService.findAllById(ids);
        }
        role.setPermissions(permissions);
    }

}
