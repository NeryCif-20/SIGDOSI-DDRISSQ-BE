package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.service;

import com.ddrissq.sigdosi.common.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.constant.BuildingMaterialErrorMessages;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialCreateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialResponse;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialSearchRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.mapper.BuildingMaterialMapper;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.model.BuildingMaterial;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.repository.BuildingMaterialRepository;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.specification.BuildingMaterialSpecification;
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
public class BuildingMaterialServiceImpl implements BuildingMaterialService {

    private final BuildingMaterialRepository repository;
    private final BuildingMaterialMapper mapper;

    @Override
    public BuildingMaterialResponse get(UUID id) {
        return null;
    }

    @Override
    public BuildingMaterialResponse create(BuildingMaterialCreateRequest request) {
        validateUniqueCode(request.code());
        BuildingMaterial buildingMaterial = mapper.toBuildingMaterial(request);
        BuildingMaterial savedBuildingMaterial = repository.save(buildingMaterial);
        return mapper.toResponse(savedBuildingMaterial);
    }

    @Override
    public BuildingMaterialResponse update(UUID id, BuildingMaterialUpdateRequest request) {
        BuildingMaterial buildingMaterial = getByIdOrThrow(id);
        String code = request.code() == null
                ? buildingMaterial.getCode()
                : request.code();
        validateUniqueCode(code, id);
        mapper.updateBuildingMaterial(request, buildingMaterial);
        return mapper.toResponse(buildingMaterial);
    }

    @Override
    public Page<BuildingMaterialResponse> getAll(BuildingMaterialSearchRequest request, Pageable pageable) {
        Specification<BuildingMaterial> spec = Specification.allOf(
                Specification.anyOf(
                        BuildingMaterialSpecification.hasCode(request.q()),
                        BuildingMaterialSpecification.hasName(request.q())));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public BuildingMaterial getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        BuildingMaterialErrorMessages.NOT_FOUND));
    }

    private void validateUniqueCode(String code) {
        validateUniqueCode(code, null);
    }

    private void validateUniqueCode(String code, UUID id) {
        String normalizedCode = code.trim().toUpperCase();
        boolean exists = id == null
                ? repository.existsByCode(normalizedCode)
                : repository.existsByCodeAndIdNot(normalizedCode, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    BuildingMaterialErrorMessages.ALREADY_EXISTS);
        }
    }

}
