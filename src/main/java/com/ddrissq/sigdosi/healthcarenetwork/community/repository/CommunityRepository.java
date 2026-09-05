package com.ddrissq.sigdosi.healthcarenetwork.community.repository;

import com.ddrissq.sigdosi.healthcarenetwork.community.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CommunityRepository extends JpaRepository<Community, UUID>, JpaSpecificationExecutor<Community> {

    boolean existsByRissIdAndNameAndTerritoryAndSector(UUID riss, String name, Integer territory, String sector);
    boolean existsByRissIdAndNameAndTerritoryAndSectorAndIdNot(UUID riss, String name, Integer territory, String sector, UUID id);

}
