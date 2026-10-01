package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.service;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanCreateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanSearchRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.model.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PlanService {

    PlanResponse get(UUID id);
    PlanResponse create(PlanCreateRequest request);
    PlanResponse update(UUID id, PlanUpdateRequest request);
    void delete(UUID id);
    Page<PlanResponse> getAll(PlanSearchRequest request, Pageable pageable);
    Plan getByIdOrThrow(UUID id);

}
