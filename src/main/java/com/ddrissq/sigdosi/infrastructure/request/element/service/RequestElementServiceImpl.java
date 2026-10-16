package com.ddrissq.sigdosi.infrastructure.request.element.service;

import com.ddrissq.sigdosi.common.exception.BusinessRuleException;
import com.ddrissq.sigdosi.common.exception.ResourceNotFoundException;
import com.ddrissq.sigdosi.common.util.ValueResolver;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.model.BuildingElement;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.service.BuildingElementService;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.model.BuildingMaterial;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.service.BuildingMaterialService;
import com.ddrissq.sigdosi.infrastructure.request.element.dto.RequestElementCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.element.dto.RequestElementResponse;
import com.ddrissq.sigdosi.infrastructure.request.element.dto.RequestElementUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.request.element.error.RequestElementErrorDescriptor;
import com.ddrissq.sigdosi.infrastructure.request.element.mapper.RequestElementMapper;
import com.ddrissq.sigdosi.infrastructure.request.element.model.RequestElement;
import com.ddrissq.sigdosi.infrastructure.request.element.model.RequestElementRequiredAction;
import com.ddrissq.sigdosi.infrastructure.request.element.repository.RequestElementRepository;
import com.ddrissq.sigdosi.infrastructure.request.model.Request;
import com.ddrissq.sigdosi.infrastructure.request.service.RequestService;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.error.FacilityElementErrorDescriptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class RequestElementServiceImpl implements RequestElementService {

    private final RequestElementRepository repository;
    private final RequestElementMapper mapper;
    private final RequestService requestService;
    private final BuildingElementService buildingElementService;
    private final BuildingMaterialService buildingMaterialService;

    @Override
    public RequestElementResponse get(UUID requestId, UUID id) {
        requestService.getByIdOrThrow(requestId);
        RequestElement requestElement = getByIdAndRequestIdOrThrow(id, requestId);
        return mapper.toResponse(requestElement);
    }

    @Override
    public List<RequestElementResponse> getAll(UUID requestId) {
        Request request = requestService.getByIdOrThrow(requestId);
        return repository.findByRequestId(request.getId()).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public RequestElementResponse create(UUID requestId, RequestElementCreateRequest request) {
        Request targetRequest = requestService.getByIdOrThrow(requestId);
        BuildingElement buildingElement = buildingElementService.getByIdOrThrow(
                request.buildingElement());
        boolean hasBuildingMaterial = buildingElement.getHasBuildingMaterial();
        if (hasBuildingMaterial && request.buildingMaterial() == null) {
            throw new BusinessRuleException(
                    FacilityElementErrorDescriptor.MATERIAL_MISSING);
        }
        BuildingMaterial buildingMaterial = hasBuildingMaterial
                ? buildingMaterialService.getByIdOrThrow(request.buildingMaterial())
                : null;
        RequestElement requestElement = RequestElement.builder()
                .request(targetRequest)
                .buildingElement(buildingElement)
                .buildingMaterial(buildingMaterial)
                .requiredAction(request.requiredAction())
                .build();
        RequestElement savedRequestElement = repository.save(requestElement);
        return mapper.toResponse(savedRequestElement);
    }

    @Override
    public RequestElementResponse update(UUID requestId, UUID id, RequestElementUpdateRequest request) {
        Request targetRequest = requestService.getByIdOrThrow(requestId);
        RequestElement requestElement = getByIdAndRequestIdOrThrow(
                id, targetRequest.getId());
        RequestElementRequiredAction requiredAction = ValueResolver.resolve(
                request.requiredAction(), requestElement.getRequiredAction());
        requestElement.setRequiredAction(requiredAction);
        BuildingElement buildingElement = ValueResolver.resolveByKey(
                request.buildingElement(),
                requestElement.getBuildingElement(),
                BuildingElement::getId,
                buildingElementService::getByIdOrThrow);
        boolean hasBuildingMaterial = buildingElement.getHasBuildingMaterial();
        boolean isBuildingMaterialMissing = request.buildingMaterial() == null
                || requestElement.getBuildingMaterial() == null;
        if (hasBuildingMaterial && isBuildingMaterialMissing) {
            throw new BusinessRuleException(
                    FacilityElementErrorDescriptor.MATERIAL_MISSING);
        }
        BuildingMaterial buildingMaterial = hasBuildingMaterial
                ? buildingMaterialService.getByIdOrThrow(request.buildingMaterial())
                : null;
        requestElement.setBuildingElement(buildingElement);
        requestElement.setBuildingMaterial(buildingMaterial);
        return mapper.toResponse(requestElement);
    }

    @Override
    public void delete(UUID requestId, UUID id) {
        Request targetRequest = requestService.getByIdOrThrow(requestId);
        RequestElement requestElement = getByIdAndRequestIdOrThrow(id, targetRequest.getId());
        repository.delete(requestElement);
    }

    private RequestElement getByIdAndRequestIdOrThrow(UUID id, UUID requestId) {
        return repository.findByIdAndRequestId(id, requestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        RequestElementErrorDescriptor.NOT_FOUND));
    }

}
