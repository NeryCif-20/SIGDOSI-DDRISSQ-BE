package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.service;

import com.ddrissq.sigdosi.common.exception.BusinessRuleException;
import com.ddrissq.sigdosi.common.exception.ResourceNotFoundException;
import com.ddrissq.sigdosi.common.util.ValueResolver;
import com.ddrissq.sigdosi.healthcarenetwork.community.model.Community;
import com.ddrissq.sigdosi.healthcarenetwork.community.service.CommunityService;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilitySearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.error.HealthFacilityErrorDescriptor;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.mapper.HealthFacilityMapper;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.repository.HealthFacilityRepository;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.specification.HealthFacilitySpecification;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.model.HealthFacilityType;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.service.HealthFacilityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
                request.type());
        validateTotalLandArea(
                request.totalLandArea(),
                request.buildingFootprint(),
                request.availableExpansionArea());
        HealthFacility healthFacility = mapper.toHealthFacility(request);
        healthFacility.setCommunity(community);
        healthFacility.setType(healthFacilityType);
        HealthFacility savedHealthFacility = repository.save(healthFacility);
        return mapper.toResponse(savedHealthFacility);
    }

    @Override
    public HealthFacilityResponse update(UUID id, HealthFacilityUpdateRequest request) {
        HealthFacility healthFacility = getByIdOrThrow(id);
        Community community = ValueResolver.resolveByKey(
                request.community(),
                healthFacility.getCommunity(),
                Community::getId,
                communityService::getByIdOrThrow);
        HealthFacilityType healthFacilityType = ValueResolver.resolveByKey(
                request.type(),
                healthFacility.getType(),
                HealthFacilityType::getId,
                healthFacilityTypeService::getByIdOrThrow);
        BigDecimal totalLandArea = ValueResolver.resolve(
                request.totalLandArea(),
                healthFacility.getTotalLandArea());
        BigDecimal buildingFootprint = ValueResolver.resolve(
                request.buildingFootprint(),
                healthFacility.getBuildingFootprint());
        BigDecimal availableExpansionArea = ValueResolver.resolve(
                request.availableExpansionArea(),
                healthFacility.getAvailableExpansionArea());
        validateTotalLandArea(totalLandArea, buildingFootprint, availableExpansionArea);
        mapper.updateHealthFacility(request, healthFacility);
        healthFacility.setCommunity(community);
        healthFacility.setType(healthFacilityType);
        return mapper.toResponse(healthFacility);
    }

    @Override
    public Page<HealthFacilityResponse> getAll(HealthFacilitySearchRequest request, Pageable pageable) {
        Specification<HealthFacility> spec = Specification.allOf(
                Specification.anyOf(
                        HealthFacilitySpecification.hasCommunityName(request.q()),
                        HealthFacilitySpecification.hasRissName(request.q())),
                HealthFacilitySpecification.hasType(request.type()),
                HealthFacilitySpecification.hasStatus(request.status()),
                HealthFacilitySpecification.isHeadquarters(request.isHeadquarters()),
                HealthFacilitySpecification.hasPropertyStatus(request.propertyStatus()),
                HealthFacilitySpecification.hasPropertyTenure(request.propertyTenure()),
                HealthFacilitySpecification.hasTotalLandAreaBetween(
                        request.minTotalLandArea(),
                        request.maxTotalLandArea()),
                HealthFacilitySpecification.hasBuildingFootprintBetween(
                        request.minBuildingFootprint(),
                        request.maxBuildingFootprint()),
                HealthFacilitySpecification.hasAvailableExpansionAreaBetween(
                        request.minAvailableExpansionArea(),
                        request.maxAvailableExpansionArea()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public HealthFacility getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HealthFacilityErrorDescriptor.NOT_FOUND));
    }

    private void validateTotalLandArea(
            BigDecimal totalLandArea,
            BigDecimal buildingFootprint,
            BigDecimal availableExpansionArea) {
        BigDecimal estimatedTotalLandArea = buildingFootprint.add(
                availableExpansionArea);
        if (estimatedTotalLandArea.compareTo(totalLandArea) > 0) {
            throw new BusinessRuleException(
                    HealthFacilityErrorDescriptor.TOTAL_LAND_AREA_EXCEEDED);
        }
    }

}
