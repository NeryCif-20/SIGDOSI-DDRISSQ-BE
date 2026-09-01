package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.service;

import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementCreateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementResponse;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementSearchRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.model.BuildingElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BuildingElementService {

    BuildingElementResponse get(UUID id);
    BuildingElementResponse create(BuildingElementCreateRequest request);
    BuildingElementResponse update(UUID id, BuildingElementUpdateRequest request);
    Page<BuildingElementResponse> getAll(BuildingElementSearchRequest request, Pageable pageable);
    BuildingElement getByIdOrThrow(UUID id);

}
