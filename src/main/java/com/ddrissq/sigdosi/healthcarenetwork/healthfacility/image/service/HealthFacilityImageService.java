package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.service;

import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto.HealthFacilityImageCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto.HealthFacilityImageDeleteRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto.HealthFacilityImageResponse;

import java.util.List;
import java.util.UUID;

public interface HealthFacilityImageService {

    List<HealthFacilityImageResponse> get(UUID healthFacilityId);
    HealthFacilityImageResponse create (UUID healthFacilityId, HealthFacilityImageCreateRequest request);
    void delete(UUID healthFacilityId, HealthFacilityImageDeleteRequest request);

}
