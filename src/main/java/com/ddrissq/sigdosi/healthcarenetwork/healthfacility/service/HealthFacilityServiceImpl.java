package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.service;

import com.ddrissq.sigdosi.common.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.common.exception.EntityValidationException;
import com.ddrissq.sigdosi.common.util.service.PatchHelper;
import com.ddrissq.sigdosi.healthcarenetwork.community.model.Community;
import com.ddrissq.sigdosi.healthcarenetwork.community.service.CommunityService;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.constant.HealthFacilityErrorMessages;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilitySearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.mapper.HealthFacilityMapper;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.repository.HealthFacilityRepository;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.model.HealthFacilityType;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.service.HealthFacilityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class HealthFacilityServiceImpl implements HealthFacilityService {

    private final HealthFacilityRepository repository;
    private final HealthFacilityMapper mapper;
    private final CommunityService communityService;
    private final HealthFacilityTypeService healthFacilityTypeService;

    @Override
    public HealthFacilityResponse get(UUID id) {
        HealthFacility healthFacility = getByIdOrThrow(id);
        return mapper.toResponse(healthFacility);
    }

    @Override
    public HealthFacilityResponse create(HealthFacilityCreateRequest request) {
        Community community = communityService.getByIdOrThrow(
                request.community());
        HealthFacilityType healthFacilityType = healthFacilityTypeService.getByIdOrThrow(
                request.healthFacilityType());
        validateTotalLandArea(
                request.totalLandArea(),
                request.buildingFootprint(),
                request.availableExpansionArea());
        HealthFacility healthFacility = mapper.toHealthFacility(request);
        healthFacility.setCommunity(community);
        healthFacility.setHealthFacilityType(healthFacilityType);
        HealthFacility savedHealthFacility = repository.save(healthFacility);
        return mapper.toResponse(savedHealthFacility);
    }

    @Override
    public HealthFacilityResponse update(UUID id, HealthFacilityUpdateRequest request) {
        HealthFacility healthFacility = getByIdOrThrow(id);
        Community community = PatchHelper.resolveEntity(
                request.community(),
                healthFacility.getCommunity(),
                communityService::getByIdOrThrow);
        HealthFacilityType healthFacilityType = PatchHelper.resolveEntity(
                request.healthFacilityType(),
                healthFacility.getHealthFacilityType(),
                healthFacilityTypeService::getByIdOrThrow);
        BigDecimal totalLandArea = PatchHelper.resolveValue(
                request.totalLandArea(),
                healthFacility.getTotalLandArea());
        BigDecimal buildingFootprint = PatchHelper.resolveValue(
                request.buildingFootprint(),
                healthFacility.getBuildingFootprint());
        BigDecimal availableExpansionArea = PatchHelper.resolveValue(
                request.availableExpansionArea(),
                healthFacility.getAvailableExpansionArea());
        validateTotalLandArea(totalLandArea, buildingFootprint, availableExpansionArea);
        mapper.updateHealthFacility(request, healthFacility);
        healthFacility.setCommunity(community);
        healthFacility.setHealthFacilityType(healthFacilityType);
        return mapper.toResponse(healthFacility);
    }

    @Override
    public Page<HealthFacilityResponse> getAll(HealthFacilitySearchRequest request, Pageable pageable) {
        return null;
    }

    @Override
    public HealthFacility getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        HealthFacilityErrorMessages.NOT_FOUND));
    }

    private void validateTotalLandArea(
            BigDecimal totalLandArea,
            BigDecimal buildingFootprint,
            BigDecimal availableExpansionArea) {
        BigDecimal estimatedTotalLandArea = buildingFootprint.add(
                availableExpansionArea);
        if (estimatedTotalLandArea.compareTo(totalLandArea) > 0) {
            throw new EntityValidationException(
                    HealthFacilityErrorMessages.TOTAL_LAND_AREA_EXCEEDED);
        }
    }

}
