package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.repository;

import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.model.HealthFacilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface HealthFacilityTypeRepository extends JpaRepository<HealthFacilityType, UUID>, JpaSpecificationExecutor<HealthFacilityType> {

    boolean existsByCodeIgnoreCase(String code);
    boolean existsByCodeIgnoreCaseAndIdNot(String code, UUID id);

}
