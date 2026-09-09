package com.ddrissq.sigdosi.healthcarenetwork.community.service;

import com.ddrissq.sigdosi.common.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.common.service.util.PatchHelper;
import com.ddrissq.sigdosi.healthcarenetwork.community.constant.CommunityErrorMessageKeys;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityResponse;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunitySearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.community.mapper.CommunityMapper;
import com.ddrissq.sigdosi.healthcarenetwork.community.model.Community;
import com.ddrissq.sigdosi.healthcarenetwork.community.repository.CommunityRepository;
import com.ddrissq.sigdosi.healthcarenetwork.community.specification.CommunitySpecification;
import com.ddrissq.sigdosi.healthcarenetwork.riss.model.Riss;
import com.ddrissq.sigdosi.healthcarenetwork.riss.service.RissService;
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
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository repository;
    private final CommunityMapper mapper;
    private final RissService rissService;

    @Override
    public CommunityResponse get(UUID id) {
        Community community =  getByIdOrThrow(id);
        return mapper.toResponse(community);
    }

    @Override
    public CommunityResponse create(CommunityCreateRequest request) {
        Riss riss = rissService.getByIdOrThrow(request.riss());
        validateUniqueRissNameTerritorySector(
                request.riss(),
                request.name(),
                request.territory(),
                request.sector());
        Community community = mapper.toCommunity(request);
        community.setRiss(riss);
        Community savedCommunity = repository.save(community);
        return mapper.toResponse(savedCommunity);
    }

    @Override
    public CommunityResponse update(UUID id, CommunityUpdateRequest request) {
        Community community = getByIdOrThrow(id);
        Riss riss = PatchHelper.resolveEntity(
                request.riss(),
                community.getRiss(),
                rissService::getByIdOrThrow);
        String name = PatchHelper.resolveValue(
                request.name(), community.getName());
        Integer territory = PatchHelper.resolveValue(
                request.territory(), community.getTerritory());
        String sector = PatchHelper.resolveValue(
                request.sector(), community.getSector());
        validateUniqueRissNameTerritorySector(
                riss.getId(), name, territory, sector, id);
        mapper.updateCommunity(request, community);
        community.setRiss(riss);
        return mapper.toResponse(community);
    }

    @Override
    public Page<CommunityResponse> getAll(CommunitySearchRequest request, Pageable pageable) {
        Specification<Community> spec = Specification.allOf(
                Specification.anyOf(
                        CommunitySpecification.hasName(request.q()),
                        CommunitySpecification.hasRissName(request.q()),
                        CommunitySpecification.hasDmsName(request.q())),
                CommunitySpecification.hasTerritory(request.territory()),
                CommunitySpecification.hasSector(request.sector()),
                CommunitySpecification.populationBetween(
                        request.minPopulation(), request.maxPopulation()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Community getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        CommunityErrorMessageKeys.NOT_FOUND));
    }

    private void validateUniqueRissNameTerritorySector(
            UUID riss, String name, Integer territory, String sector) {
        validateUniqueRissNameTerritorySector(riss, name, territory, sector, null);
    }

    private void validateUniqueRissNameTerritorySector(
            UUID riss, String name, Integer territory, String sector, UUID id) {
        String capitalizedName = StringUtils.capitalize(name.trim());
        String normalizedSector = sector.trim().toUpperCase();
        boolean exists = id == null
                ? repository.existsByRissIdAndNameAndTerritoryAndSector(
                        riss, capitalizedName, territory, normalizedSector)
                : repository.existsByRissIdAndNameAndTerritoryAndSectorAndIdNot(
                        riss, capitalizedName, territory, normalizedSector, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    CommunityErrorMessageKeys.ALREADY_EXISTS);
        }
    }

}
