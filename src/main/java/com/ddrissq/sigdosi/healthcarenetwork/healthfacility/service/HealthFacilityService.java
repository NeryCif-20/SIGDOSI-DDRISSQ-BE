package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.service;

import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilitySearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HealthFacilityService {

    HealthFacilityResponse get(UUID id);
    HealthFacilityResponse create(HealthFacilityCreateRequest request);
    HealthFacilityResponse update(UUID id, HealthFacilityUpdateRequest request);
    Page<HealthFacilityResponse> getAll(HealthFacilitySearchRequest request, Pageable pageable);
    HealthFacility getByIdOrThrow(UUID id);

}
