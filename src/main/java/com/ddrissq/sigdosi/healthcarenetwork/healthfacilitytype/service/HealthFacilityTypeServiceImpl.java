package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.service;

import com.ddrissq.sigdosi.common.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.common.service.util.PatchHelper;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.constant.HealthFacilityTypeErrorMessageKeys;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeSearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.mapper.HealthFacilityTypeMapper;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.model.HealthFacilityType;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.repository.HealthFacilityTypeRepository;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.specification.HealthFacilityTypeSpecification;
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
public class HealthFacilityTypeServiceImpl implements HealthFacilityTypeService {

    private final HealthFacilityTypeRepository repository;
    private final HealthFacilityTypeMapper mapper;

    @Override
    public HealthFacilityTypeResponse get(UUID id) {
        HealthFacilityType healthFacilityType = getByIdOrThrow(id);
        return mapper.toResponse(healthFacilityType);
    }

    @Override
    public HealthFacilityTypeResponse create(HealthFacilityTypeCreateRequest request) {
        validateUniqueCode(request.code());
        HealthFacilityType healthFacilityType = mapper.toHealthFacilityType(request);
        HealthFacilityType savedHealthFacilityType = repository.save(healthFacilityType);
        return mapper.toResponse(savedHealthFacilityType);
    }

    @Override
    public HealthFacilityTypeResponse update(UUID id, HealthFacilityTypeUpdateRequest request) {
        HealthFacilityType healthFacilityType = getByIdOrThrow(id);
        String code = PatchHelper.resolveValue(
                request.code(), healthFacilityType.getCode());
        validateUniqueCode(code, id);
        mapper.updateHealthFacilityType(request, healthFacilityType);
        return mapper.toResponse(healthFacilityType);
    }

    @Override
    public Page<HealthFacilityTypeResponse> getAll(HealthFacilityTypeSearchRequest request, Pageable pageable) {
        Specification<HealthFacilityType> spec = Specification.anyOf(
                HealthFacilityTypeSpecification.hasCode(request.q()),
                HealthFacilityTypeSpecification.hasName(request.q()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public HealthFacilityType getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        HealthFacilityTypeErrorMessageKeys.NOT_FOUND));
    }

    private void validateUniqueCode(String code) {
        validateUniqueCode(code, null);
    }

    private void validateUniqueCode(String code, UUID id) {
        String normalizedCode = code.trim().toUpperCase();
        boolean exists = id == null
                ? repository.existsByCode(normalizedCode)
                : repository.existsByCodeAndIdNot(normalizedCode, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    HealthFacilityTypeErrorMessageKeys.ALREADY_EXISTS);
        }
    }

}
