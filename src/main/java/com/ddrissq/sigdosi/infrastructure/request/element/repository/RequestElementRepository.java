package com.ddrissq.sigdosi.infrastructure.request.element.repository;

import com.ddrissq.sigdosi.infrastructure.request.element.model.RequestElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestElementRepository extends JpaRepository<RequestElement, UUID> {

    Optional<RequestElement> findByIdAndRequestId(UUID requestElementId, UUID requestId);

    List<RequestElement> findByRequestId(UUID requestId);

}
