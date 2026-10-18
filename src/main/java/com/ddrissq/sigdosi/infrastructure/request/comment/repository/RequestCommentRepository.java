package com.ddrissq.sigdosi.infrastructure.request.comment.repository;

import com.ddrissq.sigdosi.infrastructure.request.comment.model.RequestComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestCommentRepository extends JpaRepository<RequestComment, UUID> {

    List<RequestComment> findByParentIsNullAndRequestId(UUID requestId);

    List<RequestComment> findByParentIdAndRequestId(UUID requestId, UUID parentId);

    Optional<RequestComment> findByIdAndRequestId(UUID id, UUID requestId);

}
