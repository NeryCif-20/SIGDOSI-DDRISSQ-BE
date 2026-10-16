package com.ddrissq.sigdosi.infrastructure.request.attachment.service;

import com.ddrissq.sigdosi.infrastructure.request.attachment.dto.RequestAttachmentCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.attachment.dto.RequestAttachmentDeleteRequest;
import com.ddrissq.sigdosi.infrastructure.request.attachment.dto.RequestAttachmentResponse;

import java.util.List;
import java.util.UUID;

public interface RequestAttachmentService {

    List<RequestAttachmentResponse> get(UUID requestId);
    RequestAttachmentResponse create(UUID requestId, RequestAttachmentCreateRequest request);
    void delete(UUID requestId, RequestAttachmentDeleteRequest request);

}
