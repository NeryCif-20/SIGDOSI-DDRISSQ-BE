package com.ddrissq.sigdosi.healthcarenetwork.riss.repository;

import com.ddrissq.sigdosi.healthcarenetwork.riss.model.Riss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface RissRepository extends JpaRepository<Riss, UUID>, JpaSpecificationExecutor<Riss> {

    boolean existsByDms_IdAndName(UUID dms, String name);

    boolean existsByDms_IdAndNameAndIdNot(UUID dms, String name, UUID id);

}
