package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.repository;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, UUID>, JpaSpecificationExecutor<Plan> {

    boolean existsByHealthFacilityIdAndTypeIdAndVersion(UUID healthFacility, UUID type, Short version);
    boolean existsByHealthFacilityIdAndTypeIdAndVersionAndIdNot(UUID healthFacility, UUID type, Short version, UUID id);

}
