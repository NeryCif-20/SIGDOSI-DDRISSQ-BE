package com.ddrissq.sigdosi.healthcarenetwork.community.service;

import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityResponse;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunitySearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.community.model.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CommunityService {

    CommunityResponse get(UUID id);
    CommunityResponse create(CommunityCreateRequest request);
    CommunityResponse update(UUID id, CommunityUpdateRequest request);
    Page<CommunityResponse> getAll(CommunitySearchRequest request, Pageable pageable);
    Community getByIdOrThrow(UUID id);

}
