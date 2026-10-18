package com.ddrissq.sigdosi.infrastructure.request.comment.service;

import com.ddrissq.sigdosi.infrastructure.request.comment.dto.RequestCommentCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.comment.dto.RequestCommentResponse;
import com.ddrissq.sigdosi.infrastructure.request.comment.dto.RequestCommentUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface RequestCommentService {

    List<RequestCommentResponse> getByRequestAndParentIsNull(UUID requestId);
    RequestCommentResponse create(UUID requestId, RequestCommentCreateRequest request);
    RequestCommentResponse update(UUID id, UUID requestId, RequestCommentUpdateRequest request);
    void delete(UUID id, UUID requestId);
    List<RequestCommentResponse> getReplies(UUID id, UUID requestId);


}
