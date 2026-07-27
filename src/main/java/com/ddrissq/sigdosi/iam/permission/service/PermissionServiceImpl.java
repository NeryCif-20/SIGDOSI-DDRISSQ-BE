package com.ddrissq.sigdosi.iam.permission.service;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionCreateRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionSearchRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionUpdateRequest;
import com.ddrissq.sigdosi.iam.permission.mapper.PermissionMapper;
import com.ddrissq.sigdosi.iam.permission.model.Permission;
import com.ddrissq.sigdosi.iam.permission.repository.PermissionRepository;
import com.ddrissq.sigdosi.iam.permission.specification.PermissionSpecification;
import com.ddrissq.sigdosi.shared.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.shared.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository repository;
    private final PermissionMapper mapper;

    @Override
    public PermissionResponse get(UUID id) {
        Permission permission = findById(id);
        return mapper.toResponse(permission);
    }

    @Override
    public PermissionResponse create(PermissionCreateRequest request) {
        boolean alreadyExists = repository.existsByModuleAndAction(
                request.module(), request.action());
        if (alreadyExists) {
            throw new EntityAlreadyExistsException(
                    "Ya existe un permiso con el modulo y acción especificados.");
        }
        Permission permission = mapper.toPermission(request);
        Permission savedPermission = repository.save(permission);
        return mapper.toResponse(savedPermission);
    }

    @Override
    public PermissionResponse update(UUID id, PermissionUpdateRequest request) {
        Permission permission = findById(id);
        mapper.updatePermission(request, permission);
        Permission savedPermission = repository.save(permission);
        return mapper.toResponse(savedPermission);
    }

    @Override
    public void delete(UUID id) {
        Permission permission = findById(id);
        repository.delete(permission);
    }

    @Override
    public Page<PermissionResponse> getAll(Pageable pageable, PermissionSearchRequest request) {
        Specification<Permission> spec = Specification.allOf(
                PermissionSpecification.hasModule(request.module()),
                PermissionSpecification.hasAction(request.action()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Permission findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "El permiso solicitado no fue encontrado"));
    }
}
