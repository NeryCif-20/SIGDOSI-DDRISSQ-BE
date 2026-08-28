package com.ddrissq.sigdosi.healthcarenetwork.dms.service;


import com.ddrissq.sigdosi.common.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.healthcarenetwork.dms.constant.DmsErrorMessages;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsResponse;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsSearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.mapper.DmsMapper;
import com.ddrissq.sigdosi.healthcarenetwork.dms.model.Dms;
import com.ddrissq.sigdosi.healthcarenetwork.dms.repository.DmsRepository;
import com.ddrissq.sigdosi.healthcarenetwork.dms.specification.DmsSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class DmsServiceImpl implements DmsService {

    private final DmsRepository repository;
    private final DmsMapper mapper;

    @Override
    public DmsResponse get(UUID id) {
        Dms dms = getByIdOrThrow(id);
        return mapper.toResponse(dms);
    }

    @Override
    public DmsResponse create(DmsCreateRequest request) {
        validateUniqueName(request.name());
        Dms dms = mapper.toDms(request);
        Dms savedDms = repository.save(dms);
        return mapper.toResponse(savedDms);
    }

    @Override
    public DmsResponse update(UUID id, DmsUpdateRequest request) {
        Dms dms =  getByIdOrThrow(id);
        validateUniqueName(request.name(), id);
        mapper.updateDms(request, dms);
        return mapper.toResponse(dms);
    }

    @Override
    public Page<DmsResponse> getAll(DmsSearchRequest request, Pageable pageable) {
        Specification<Dms> spec = Specification.allOf(
                DmsSpecification.hasName(request.name()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Dms getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        DmsErrorMessages.NOT_FOUND));
    }

    private void validateUniqueName(String name) {
        validateUniqueName(name, null);
    }

    private void validateUniqueName(String name, UUID id) {
        String normalizedName = name.trim().toUpperCase();
        boolean exists = id == null
                ? repository.existsByName(normalizedName)
                : repository.existsByNameAndIdNot(normalizedName, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    DmsErrorMessages.ALREADY_EXISTS);
        }
    }

}
