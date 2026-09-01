package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.repository;

import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.model.BuildingElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BuildingElementRepository extends JpaRepository<BuildingElement, UUID>, JpaSpecificationExecutor<BuildingElement> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);

}
