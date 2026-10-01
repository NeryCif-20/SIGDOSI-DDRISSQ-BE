package com.ddrissq.sigdosi.healthcarenetwork.dms.repository;

import com.ddrissq.sigdosi.healthcarenetwork.dms.model.Dms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DmsRepository extends JpaRepository<Dms, UUID>, JpaSpecificationExecutor<Dms> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);

}
