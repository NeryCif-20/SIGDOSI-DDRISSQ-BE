package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.repository;

import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.model.HealthFacilityImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HealthFacilityImageRepository extends JpaRepository<HealthFacilityImage, UUID> {

    List<HealthFacilityImage> findByHealthFacilityId(UUID healthFacility);

    int countByHealthFacilityId(UUID healthFacility);

}
