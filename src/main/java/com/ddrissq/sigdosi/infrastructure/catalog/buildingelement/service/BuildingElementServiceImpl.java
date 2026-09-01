package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.service;

import com.ddrissq.sigdosi.common.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.constant.BuildingElementErrorMessages;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementCreateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementResponse;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementSearchRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.mapper.BuildingElementMapper;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.model.BuildingElement;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.repository.BuildingElementRepository;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.specification.BuildingElementSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class BuildingElementServiceImpl implements BuildingElementService {

    private final BuildingElementRepository repository;
    private final BuildingElementMapper mapper;

    @Override
    public BuildingElementResponse get(UUID id) {
        BuildingElement buildingElement = getByIdOrThrow(id);
        return mapper.toResponse(buildingElement);
    }

    @Override
    public BuildingElementResponse create(BuildingElementCreateRequest request) {
        validateUniqueName(request.name());
        BuildingElement buildingElement = mapper.toBuildingElement(request);
        BuildingElement savedBuildingElement = repository.save(buildingElement);
        return mapper.toResponse(savedBuildingElement);
    }

    @Override
    public BuildingElementResponse update(UUID id, BuildingElementUpdateRequest request) {
        BuildingElement buildingElement = getByIdOrThrow(id);
        String name = request.name() == null
                ? buildingElement.getName()
                : request.name();
        validateUniqueName(name, id);
        mapper.updateBuildingElement(request, buildingElement);
        return mapper.toResponse(buildingElement);
    }

    @Override
    public Page<BuildingElementResponse> getAll(BuildingElementSearchRequest request, Pageable pageable) {
        Specification<BuildingElement> spec = Specification.allOf(
                BuildingElementSpecification.hasName(request.q()),
                BuildingElementSpecification.hasBuildingMaterial(request.hasBuildingMaterial()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public BuildingElement getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        BuildingElementErrorMessages.NOT_FOUND));
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
                    BuildingElementErrorMessages.ALREADY_EXISTS);
        }
    }

}
