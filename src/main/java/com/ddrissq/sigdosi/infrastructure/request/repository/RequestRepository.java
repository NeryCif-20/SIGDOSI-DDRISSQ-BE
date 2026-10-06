package com.ddrissq.sigdosi.infrastructure.request.repository;

import com.ddrissq.sigdosi.infrastructure.request.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, UUID>, JpaSpecificationExecutor<Request> {

    @Query(value = "SELECT nextval(request_reference_code)", nativeQuery = true)
    long getNextValue();

}
