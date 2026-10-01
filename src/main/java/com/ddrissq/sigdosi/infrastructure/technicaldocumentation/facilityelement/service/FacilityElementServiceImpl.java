package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.service;

import com.ddrissq.sigdosi.common.exception.BusinessRuleException;
import com.ddrissq.sigdosi.common.exception.ResourceAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.ResourceNotFoundException;
import com.ddrissq.sigdosi.common.util.ValueResolver;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.service.HealthFacilityService;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.model.BuildingElement;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.service.BuildingElementService;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.model.BuildingMaterial;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.service.BuildingMaterialService;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementCreateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementSearchRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.error.FacilityElementErrorDescriptor;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.mapper.FacilityElementMapper;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.model.FacilityElement;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.repository.FacilityElementRepository;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.specification.FacilityElementSpecification;
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
public class FacilityElementServiceImpl implements FacilityElementService {

    private final FacilityElementRepository repository;
    private final FacilityElementMapper mapper;
    private final HealthFacilityService healthFacilityService;
    private final BuildingElementService buildingElementService;
    private final BuildingMaterialService buildingMaterialService;

    @Override
    public FacilityElementResponse get(UUID id) {
        FacilityElement facilityElement = getByIdOrThrow(id);
        return mapper.toResponse(facilityElement);
    }

    @Override
    public FacilityElementResponse create(FacilityElementCreateRequest request) {
        HealthFacility healthFacility = healthFacilityService.getByIdOrThrow(
                request.healthFacility());
        BuildingElement buildingElement = buildingElementService.getByIdOrThrow(
                request.buildingElement());
        validateUniqueHealthFacilityBuildingElement(
                healthFacility.getId(),
                buildingElement.getId());
        boolean hasBuildingMaterial = buildingElement.getHasBuildingMaterial();
        if (hasBuildingMaterial && request.buildingMaterial() == null) {
            throw new BusinessRuleException(
                    FacilityElementErrorDescriptor.MATERIAL_MISSING);
        }
        BuildingMaterial buildingMaterial = hasBuildingMaterial
                ? buildingMaterialService.getByIdOrThrow(request.buildingMaterial())
                : null;
        FacilityElement facilityElement = FacilityElement.builder()
                .healthFacility(healthFacility)
                .buildingElement(buildingElement)
                .buildingMaterial(buildingMaterial)
                .build();
        FacilityElement savedFacilityElement = repository.save(facilityElement);
        return mapper.toResponse(savedFacilityElement);
    }

    @Override
    public FacilityElementResponse update(UUID id, FacilityElementUpdateRequest request) {
        FacilityElement facilityElement = getByIdOrThrow(id);
        BuildingElement buildingElement = ValueResolver.resolveByKey(
                request.buildingElement(),
                facilityElement.getBuildingElement(),
                BuildingElement::getId,
                buildingElementService::getByIdOrThrow);
        validateUniqueHealthFacilityBuildingElement(
                facilityElement.getHealthFacility().getId(),
                buildingElement.getId(),
                id);
        boolean hasBuildingMaterial = buildingElement.getHasBuildingMaterial();
        boolean isBuildingMaterialMissing = request.buildingMaterial() == null
                || facilityElement.getBuildingMaterial() == null;
        if (hasBuildingMaterial && isBuildingMaterialMissing) {
            throw new BusinessRuleException(
                    FacilityElementErrorDescriptor.MATERIAL_MISSING);
        }
        BuildingMaterial buildingMaterial = hasBuildingMaterial
                ? buildingMaterialService.getByIdOrThrow(request.buildingMaterial())
                : null;
        facilityElement.setBuildingElement(buildingElement);
        facilityElement.setBuildingMaterial(buildingMaterial);
        return mapper.toResponse(facilityElement);
    }

    @Override
    public void delete(UUID id) {
        FacilityElement facilityElement = getByIdOrThrow(id);
        repository.delete(facilityElement);
    }

    @Override
    public Page<FacilityElementResponse> getAll(FacilityElementSearchRequest request, Pageable pageable) {
        Specification<FacilityElement> spec = Specification.allOf(
                FacilityElementSpecification.hasBuildingElementName(request.q()),
                FacilityElementSpecification.hasHealthFacility(request.healthFacility()),
                FacilityElementSpecification.hasBuildingMaterial(request.buildingMaterial()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public FacilityElement getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        FacilityElementErrorDescriptor.NOT_FOUND));
    }

    private void validateUniqueHealthFacilityBuildingElement(UUID healthFacility, UUID buildingElement) {
        validateUniqueHealthFacilityBuildingElement(healthFacility, buildingElement, null);
    }

    private void validateUniqueHealthFacilityBuildingElement(UUID healthFacility, UUID buildingElement, UUID id) {
        boolean exists = id == null
                ? repository.existsByHealthFacilityIdAndBuildingElementId(
                        healthFacility, buildingElement)
                : repository.existsByHealthFacilityIdAndBuildingElementIdAndIdNot(
                        healthFacility, buildingElement, id);
        if (exists) {
            throw new ResourceAlreadyExistsException(
                    FacilityElementErrorDescriptor.ALREADY_EXISTS);
        }
    }

}
