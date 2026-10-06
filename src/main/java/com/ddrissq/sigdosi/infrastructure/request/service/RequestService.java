package com.ddrissq.sigdosi.infrastructure.request.service;

import com.ddrissq.sigdosi.infrastructure.request.dto.RequestCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestResponse;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestSearchRequest;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.request.model.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RequestService {

    RequestResponse get(UUID id);
    RequestResponse create(RequestCreateRequest request);
    RequestResponse update(UUID id, RequestUpdateRequest request);
    void delete(UUID id);
    Page<RequestResponse> getAll(RequestSearchRequest request, Pageable pageable);
    Request getByIdOrThrow(UUID id);

}
