package com.ddrissq.sigdosi.iam.role.service;

import com.ddrissq.sigdosi.common.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.common.service.util.PatchHelper;
import com.ddrissq.sigdosi.iam.permission.service.PermissionService;
import com.ddrissq.sigdosi.iam.role.constant.RoleErrorMessageKeys;
import com.ddrissq.sigdosi.iam.role.dto.RoleCreateRequest;
import com.ddrissq.sigdosi.iam.role.dto.RoleResponse;
import com.ddrissq.sigdosi.iam.role.dto.RoleSearchRequest;
import com.ddrissq.sigdosi.iam.role.dto.RoleUpdateRequest;
import com.ddrissq.sigdosi.iam.role.mapper.RoleMapper;
import com.ddrissq.sigdosi.iam.role.model.Role;
import com.ddrissq.sigdosi.iam.role.repository.RoleRepository;
import com.ddrissq.sigdosi.iam.role.specification.RoleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleRepository repository;
    private final RoleMapper mapper;
    private final PermissionService permissionService;
    private final MessageService messageService;

    @Override
    public RoleResponse get(UUID id) {
        Role role = getByIdOrThrow(id);
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
        Role role = getByIdOrThrow(id);
        String name = PatchHelper.resolveValue(
                request.name(), role.getName());
        validateUniqueName(name, id);
        mapper.updateRole(request, role);
        updatePermissions(request.permissions(), role);
        return mapper.toResponse(role);
    }

    @Override
    public Page<RoleResponse> getAll(RoleSearchRequest request, Pageable pageable) {
        Specification<Role> spec = Specification.allOf(
                Specification.anyOf(
                        RoleSpecification.hasName(request.q()),
                        RoleSpecification.hasModule(request.q())),
                RoleSpecification.hasAction(request.action()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Role getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageService.getMessage(
                                RoleErrorMessageKeys.NOT_FOUND)));
    }

    private void validateUniqueName(String name) {
        validateUniqueName(name, null);
    }

    private void validateUniqueName(String name, UUID id) {
        String capitalizedName = StringUtils.capitalize(name.trim());
        boolean exists = id == null
                ? repository.existsByName(capitalizedName)
                : repository.existsByNameAndIdNot(capitalizedName, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    messageService.getMessage(
                            RoleErrorMessageKeys.ALREADY_EXISTS));
        }
    }

    private void updatePermissions(List<UUID> ids, Role role) {
        if (ids != null && !ids.isEmpty()) {
            role.setPermissions(permissionService.getAllById(ids));
        }
    }

}
