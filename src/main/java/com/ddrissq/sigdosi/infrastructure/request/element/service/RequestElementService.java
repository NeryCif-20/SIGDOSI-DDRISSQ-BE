package com.ddrissq.sigdosi.infrastructure.request.element.service;

import com.ddrissq.sigdosi.infrastructure.request.element.dto.RequestElementCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.element.dto.RequestElementResponse;
import com.ddrissq.sigdosi.infrastructure.request.element.dto.RequestElementUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface RequestElementService {

    RequestElementResponse get(UUID requestId, UUID id);
    List<RequestElementResponse> getAll(UUID requestId);
    RequestElementResponse create(UUID requestId, RequestElementCreateRequest request);
    RequestElementResponse update(UUID requestId, UUID id, RequestElementUpdateRequest request);
    void delete(UUID requestId, UUID id);

}
