package com.ddrissq.sigdosi.infrastructure.request.attachment.repository;

import com.ddrissq.sigdosi.infrastructure.request.attachment.model.RequestAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RequestAttachmentRepository extends JpaRepository<RequestAttachment, UUID> {

    List<RequestAttachment> findByRequestId(UUID requestId);

    int countByRequestId(UUID requestId);

}
