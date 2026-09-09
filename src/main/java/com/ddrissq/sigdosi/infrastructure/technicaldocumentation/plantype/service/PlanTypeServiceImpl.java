package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.service;

import com.ddrissq.sigdosi.common.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.constant.BuildingElementErrorMessageKeys;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.constant.PlanTypeErrorMessageKeys;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeCreateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeSearchRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.mapper.PlanTypeMapper;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.model.PlanType;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.repository.PlanTypeRepository;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.specification.PlanTypeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class PlanTypeServiceImpl implements PlanTypeService {

    private final PlanTypeRepository repository;
    private final PlanTypeMapper mapper;

    @Override
    public PlanTypeResponse get(UUID id) {
        PlanType planType = getByIdOrThrow(id);
        return mapper.toResponse(planType);
    }

    @Override
    public PlanTypeResponse create(PlanTypeCreateRequest request) {
        validateUniqueCode(request.code());
        PlanType planType = mapper.toPlanType(request);
        PlanType savedPlanType = repository.save(planType);
        return mapper.toResponse(savedPlanType);
    }

    @Override
    public PlanTypeResponse update(UUID id, PlanTypeUpdateRequest request) {
        PlanType planType = getByIdOrThrow(id);
        String code = request.code() == null
                ? planType.getCode()
                : request.code();
        validateUniqueCode(code, id);
        mapper.updatePlanType(request, planType);
        return mapper.toResponse(planType);
    }

    @Override
    public Page<PlanTypeResponse> getAll(PlanTypeSearchRequest request, Pageable pageable) {
        Specification<PlanType> spec = Specification.allOf(
                Specification.anyOf(
                        PlanTypeSpecification.hasCode(request.q()),
                        PlanTypeSpecification.hasName(request.q())));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public PlanType getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        PlanTypeErrorMessageKeys.NOT_FOUND));
    }

    private void validateUniqueCode(String name) {
        validateUniqueCode(name, null);
    }

    private void validateUniqueCode(String name, UUID id) {
        String capitalizedName = StringUtils.capitalize(name.trim());
        boolean exists = id == null
                ? repository.existsByCode(capitalizedName)
                : repository.existsByCodeAndIdNot(capitalizedName, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    BuildingElementErrorMessageKeys.ALREADY_EXISTS);
        }
    }

}
