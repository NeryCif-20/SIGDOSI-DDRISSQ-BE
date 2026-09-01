package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.repository;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.model.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PlanTypeRepository extends JpaRepository<PlanType, UUID>, JpaSpecificationExecutor<PlanType> {

    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, UUID id);

}
