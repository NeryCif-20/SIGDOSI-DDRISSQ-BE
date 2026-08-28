package com.ddrissq.sigdosi.healthcarenetwork.riss.service;

import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissResponse;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissSearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.riss.model.Riss;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RissService {

    RissResponse get(UUID id);
    RissResponse create(RissCreateRequest request);
    RissResponse update(UUID id, RissUpdateRequest request);
    Page<RissResponse> getAll(RissSearchRequest request, Pageable pageable);
    Riss getByIdOrThrow(UUID id);

}
