package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.service;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeCreateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeSearchRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.model.PlanType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PlanTypeService {

    PlanTypeResponse get(UUID id);
    PlanTypeResponse create(PlanTypeCreateRequest request);
    PlanTypeResponse update(UUID id, PlanTypeUpdateRequest request);
    Page<PlanTypeResponse> getAll(PlanTypeSearchRequest request, Pageable pageable);
    PlanType getByIdOrThrow(UUID id);

}
