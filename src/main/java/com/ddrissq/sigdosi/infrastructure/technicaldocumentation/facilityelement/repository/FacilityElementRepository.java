package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.repository;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.model.FacilityElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FacilityElementRepository extends JpaRepository<FacilityElement, UUID>, JpaSpecificationExecutor<FacilityElement> {

    boolean existsByHealthFacilityIdAndBuildingElementId(UUID healthFacility, UUID buildingElement);
    boolean existsByHealthFacilityIdAndBuildingElementIdAndIdNot(UUID healthFacility, UUID buildingElement, UUID id);

}
