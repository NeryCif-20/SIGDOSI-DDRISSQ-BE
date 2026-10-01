package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.service;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementCreateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementSearchRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.model.FacilityElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FacilityElementService {

    FacilityElementResponse get(UUID id);
    FacilityElementResponse create(FacilityElementCreateRequest request);
    FacilityElementResponse update(UUID id, FacilityElementUpdateRequest request);
    void delete(UUID id);
    Page<FacilityElementResponse> getAll(FacilityElementSearchRequest request, Pageable pageable);
    FacilityElement getByIdOrThrow(UUID id);

}
