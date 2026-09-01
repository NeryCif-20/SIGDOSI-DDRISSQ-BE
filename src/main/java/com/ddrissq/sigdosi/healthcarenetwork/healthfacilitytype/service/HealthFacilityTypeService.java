package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.service;

import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeSearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.model.HealthFacilityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HealthFacilityTypeService {

    HealthFacilityTypeResponse get(UUID id);
    HealthFacilityTypeResponse create(HealthFacilityTypeCreateRequest request);
    HealthFacilityTypeResponse update(UUID id, HealthFacilityTypeUpdateRequest request);
    Page<HealthFacilityTypeResponse> getAll(HealthFacilityTypeSearchRequest request, Pageable pageable);
    HealthFacilityType getByIdOrThrow(UUID id);

}
