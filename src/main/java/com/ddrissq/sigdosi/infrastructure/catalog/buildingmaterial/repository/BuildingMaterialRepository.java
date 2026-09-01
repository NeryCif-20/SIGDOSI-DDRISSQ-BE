package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.repository;

import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.model.BuildingMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BuildingMaterialRepository extends JpaRepository<BuildingMaterial, UUID>, JpaSpecificationExecutor<BuildingMaterial> {

    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, UUID id);

}
