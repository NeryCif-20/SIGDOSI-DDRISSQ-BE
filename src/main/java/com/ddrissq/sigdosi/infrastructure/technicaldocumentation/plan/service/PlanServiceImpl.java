package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.service;

import com.ddrissq.sigdosi.common.exception.ResourceAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.ResourceNotFoundException;
import com.ddrissq.sigdosi.common.file.storage.model.StorageFolder;
import com.ddrissq.sigdosi.common.file.storage.service.StorageService;
import com.ddrissq.sigdosi.common.util.ValueResolver;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.service.HealthFacilityService;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.model.PlanType;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.service.PlanTypeService;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanCreateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanSearchRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.error.PlanErrorDescriptor;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.mapper.PlanMapper;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.model.Plan;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.repository.PlanRepository;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.specifcation.PlanSpecification;
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
public class PlanServiceImpl implements PlanService {

    private final PlanRepository repository;
    private final PlanMapper mapper;
    private final PlanTypeService planTypeService;
    private final HealthFacilityService healthFacilityService;
    private final StorageService storageService;

    @Override
    public PlanResponse get(UUID id) {
        Plan plan = getByIdOrThrow(id);
        return mapper.toResponse(plan);
    }

    @Override
    public PlanResponse create(PlanCreateRequest request) {
        PlanType type = planTypeService.getByIdOrThrow(request.type());
        HealthFacility healthFacility = healthFacilityService.getByIdOrThrow(
                request.healthFacility());
        validateUniqueHealthFacilityTypeVersion(
                healthFacility.getId(), type.getId(), request.version());
        String path = storageService.save(request.file(), StorageFolder.PLANS);
        Plan plan = mapper.toPlan(request);
        plan.setHealthFacility(healthFacility);
        plan.setType(type);
        plan.setPath(path);
        Plan savedPlan = repository.save(plan);
        return mapper.toResponse(savedPlan);
    }

    @Override
    public PlanResponse update(UUID id, PlanUpdateRequest request) {
        Plan plan = getByIdOrThrow(id);
        PlanType type = ValueResolver.resolveByKey(
                request.type(),
                plan.getType(),
                PlanType::getId,
                planTypeService::getByIdOrThrow);
        Short version = ValueResolver.resolve(request.version(), plan.getVersion());
        validateUniqueHealthFacilityTypeVersion(
                plan.getHealthFacility().getId(),
                type.getId(),
                version,
                id);
        mapper.updatePlan(request, plan);
        if (request.file() != null) {
            String oldPath = plan.getPath();
            String path = storageService.save(request.file(), StorageFolder.PLANS);
            plan.setPath(path);
            storageService.delete(oldPath);
        }
        return mapper.toResponse(plan);
    }

    @Override
    public void delete(UUID id) {
        Plan plan = getByIdOrThrow(id);
        String path = plan.getPath();
        repository.delete(plan);
        storageService.delete(path);
    }

    @Override
    public Page<PlanResponse> getAll(PlanSearchRequest request, Pageable pageable) {
        Specification<Plan> spec = Specification.allOf(
                PlanSpecification.hasHealthFacility(request.healthFacility()),
                PlanSpecification.hasType(request.type()),
                PlanSpecification.hasVersion(request.version()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Plan getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        PlanErrorDescriptor.NOT_FOUND));
    }

    private void validateUniqueHealthFacilityTypeVersion(UUID healthFacility, UUID type, Short version) {
        validateUniqueHealthFacilityTypeVersion(healthFacility, type, version, null);
    }

    private void validateUniqueHealthFacilityTypeVersion(UUID healthFacility, UUID type, Short version, UUID id) {
        boolean exists = id == null
                ? repository.existsByHealthFacilityIdAndTypeIdAndVersion(
                        healthFacility, type, version)
                : repository.existsByHealthFacilityIdAndTypeIdAndVersionAndIdNot(
                        healthFacility, type, version, id);
        if (exists) {
            throw new ResourceAlreadyExistsException(
                    PlanErrorDescriptor.ALREADY_EXISTS);
        }
    }

}
