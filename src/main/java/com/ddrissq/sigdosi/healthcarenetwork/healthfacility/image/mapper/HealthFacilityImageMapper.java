package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.mapper;

import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto.HealthFacilityImageResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.model.HealthFacilityImage;
import org.mapstruct.Mapper;

@Mapper()
public interface HealthFacilityImageMapper {

    HealthFacilityImageResponse toResponse(HealthFacilityImage healthFacilityImage);

}
