package com.ddrissq.sigdosi.healthcarenetwork.community.repository;

import com.ddrissq.sigdosi.healthcarenetwork.community.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CommunityRepository extends JpaRepository<Community, UUID>, JpaSpecificationExecutor<Community> {

    boolean existsByRissIdAndNameIgnoreCaseAndTerritoryAndSectorIgnoreCase(UUID riss, String name, Short territory, String sector);
    boolean existsByRissIdAndNameIgnoreCaseAndTerritoryAndSectorIgnoreCaseAndIdNot(UUID riss, String name, Short territory, String sector, UUID id);

}
