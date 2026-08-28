package com.ddrissq.sigdosi.iam.permission.service;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionCreateRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionSearchRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionUpdateRequest;
import com.ddrissq.sigdosi.iam.permission.constant.PermissionErrorMessages;
import com.ddrissq.sigdosi.iam.permission.mapper.PermissionMapper;
import com.ddrissq.sigdosi.iam.permission.model.Permission;
import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import com.ddrissq.sigdosi.iam.permission.repository.PermissionRepository;
import com.ddrissq.sigdosi.iam.permission.specification.PermissionSpecification;
import com.ddrissq.sigdosi.common.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository repository;
    private final PermissionMapper mapper;

    @Override
    public PermissionResponse get(UUID id) {
        Permission permission = getByIdOrThrow(id);
        return mapper.toResponse(permission);
    }

    @Override
    public PermissionResponse create(PermissionCreateRequest request) {
        validateUniqueModuleAction(request.module(), request.action());
        Permission permission = mapper.toPermission(request);
        Permission savedPermission = repository.save(permission);
        return mapper.toResponse(savedPermission);
    }

    @Override
    public PermissionResponse update(UUID id, PermissionUpdateRequest request) {
        Permission permission = getByIdOrThrow(id);
        String module = request.module() == null
                ? permission.getModule()
                : request.module();
        PermissionAction action = request.action() == null
                ? permission.getAction()
                : request.action();
        validateUniqueModuleAction(module, action, id);
        mapper.updatePermission(request, permission);
        return mapper.toResponse(permission);
    }

    @Override
    public Page<PermissionResponse> getAll(PermissionSearchRequest request, Pageable pageable) {
        Specification<Permission> spec = Specification.allOf(
                PermissionSpecification.hasModule(request.module()),
                PermissionSpecification.hasAction(request.action()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Permission getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        PermissionErrorMessages.NOT_FOUND));
    }

    @Override
    public Set<Permission> getAllById(Set<UUID> ids) {
        return repository.findAllById(ids)
                .stream()
                .collect(Collectors.toUnmodifiableSet());
    }

    private void validateUniqueModuleAction(String module, PermissionAction action) {
        validateUniqueModuleAction(module, action, null);
    }

    private void validateUniqueModuleAction(String module, PermissionAction action, UUID id) {
        String normalizedModule = module.trim().toUpperCase();
        boolean exists = id == null
                ? repository.existsByModuleAndAction(normalizedModule, action)
                : repository.existsByModuleAndActionAndIdNot(normalizedModule, action, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    PermissionErrorMessages.ALREADY_EXISTS);
        }
    }

}
