package com.ddrissq.sigdosi.infrastructure.request.service;

import com.ddrissq.sigdosi.common.exception.ResourceNotFoundException;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.service.HealthFacilityService;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestResponse;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestSearchRequest;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.request.error.RequestErrorDescriptor;
import com.ddrissq.sigdosi.infrastructure.request.mapper.RequestMapper;
import com.ddrissq.sigdosi.infrastructure.request.model.Request;
import com.ddrissq.sigdosi.infrastructure.request.repository.RequestRepository;
import com.ddrissq.sigdosi.infrastructure.request.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private final RequestRepository repository;
    private final RequestMapper mapper;
    private final HealthFacilityService healthFacilityService;

    @Override
    public RequestResponse get(UUID id) {
        Request request = getByIdOrThrow(id);
        return mapper.toResponse(request);
    }

    @Override
    public RequestResponse create(RequestCreateRequest request) {
        HealthFacility healthFacility = healthFacilityService.getByIdOrThrow(
                request.healthFacility());
        Request newRequest = mapper.toRequest(request);
        newRequest.setHealthFacility(healthFacility);
        newRequest.setReferenceCode(generateReferenceCode());
        Request savedRequest = repository.save(newRequest);
        return mapper.toResponse(savedRequest);
    }

    @Override
    public RequestResponse update(UUID id, RequestUpdateRequest request) {
        Request currentRequest = getByIdOrThrow(id);
        mapper.updateRequest(request, currentRequest);
        return mapper.toResponse(currentRequest);
    }

    @Override
    public void delete(UUID id) {
        Request request = getByIdOrThrow(id);
        repository.delete(request);
    }

    @Override
    public Page<RequestResponse> getAll(RequestSearchRequest request, Pageable pageable) {
        Specification<Request> spec = Specification.allOf(
                Specification.anyOf(
                        RequestSpecification.hasReferenceCode(request.q()),
                        RequestSpecification.hasCommunityName(request.q()),
                        RequestSpecification.hasRissName(request.q())),
                RequestSpecification.hasStatus(request.status()),
                RequestSpecification.aproximateCostBetween(
                        request.minAproximateCost(), request.maxAproximateCost()),
                RequestSpecification.requestedDateBetween(
                        request.minRequestedDate(), request.maxRequestedDate()));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Request getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        RequestErrorDescriptor.NOT_FOUND));
    }

    private String generateReferenceCode() {
        long nextValue = repository.getNextValue();
        String generatedCode = Long.toString(nextValue, 36).toUpperCase();
        String formattedCode = String.format("%6s", generatedCode).replace(' ', '0');
        int year = Year.now().getValue();
        return String.format("%d-%s", year, formattedCode);
    }

}
