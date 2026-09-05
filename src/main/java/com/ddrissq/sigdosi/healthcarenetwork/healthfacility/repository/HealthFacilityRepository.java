package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.repository;

import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface HealthFacilityRepository extends JpaRepository<HealthFacility, UUID>, JpaSpecificationExecutor<HealthFacility> {

}
