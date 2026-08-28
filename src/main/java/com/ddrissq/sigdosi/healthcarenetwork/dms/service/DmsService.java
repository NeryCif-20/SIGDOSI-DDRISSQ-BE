package com.ddrissq.sigdosi.healthcarenetwork.dms.service;

import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsResponse;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsSearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.model.Dms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DmsService {

    DmsResponse get(UUID id);
    DmsResponse create(DmsCreateRequest request);
    DmsResponse update(UUID id, DmsUpdateRequest request);
    Page<DmsResponse> getAll(DmsSearchRequest request, Pageable pageable);
    Dms getByIdOrThrow(UUID id);

}
