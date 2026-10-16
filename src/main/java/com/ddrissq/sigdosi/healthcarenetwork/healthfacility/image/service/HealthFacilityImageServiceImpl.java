package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.service;

import com.ddrissq.sigdosi.common.exception.BusinessRuleException;
import com.ddrissq.sigdosi.common.file.storage.model.StorageDeletedEvent;
import com.ddrissq.sigdosi.common.file.storage.model.StoragePath;
import com.ddrissq.sigdosi.common.file.storage.model.StorageResource;
import com.ddrissq.sigdosi.common.file.storage.service.StorageService;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.configuration.HealthFacilityImageProperties;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto.HealthFacilityImageCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto.HealthFacilityImageDeleteRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto.HealthFacilityImageResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.error.HealthFacilityImageErrorDescriptor;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.mapper.HealthFacilityImageMapper;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.model.HealthFacilityImage;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.repository.HealthFacilityImageRepository;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.service.HealthFacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class HealthFacilityImageServiceImpl implements HealthFacilityImageService {

    private final HealthFacilityImageRepository repository;
    private final HealthFacilityImageMapper mapper;
    private final HealthFacilityService healthFacilityService;
    private final HealthFacilityImageProperties props;
    private final ApplicationEventPublisher eventPublisher;
    private final StorageService storageService;

    @Override
    public List<HealthFacilityImageResponse> get(UUID healthFacilityId) {
        healthFacilityService.getByIdOrThrow(healthFacilityId);
        return repository
                .findByHealthFacilityId(healthFacilityId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public HealthFacilityImageResponse create(UUID healthFacilityId, HealthFacilityImageCreateRequest request) {
        HealthFacility healthFacility = healthFacilityService.getByIdOrThrow(healthFacilityId);
        int currentCount = repository.countByHealthFacilityId(healthFacilityId);
        if (currentCount > props.limit()) {
            throw new BusinessRuleException(
                    HealthFacilityImageErrorDescriptor.LIMIT_EXCEEDED);
        }
        String path = storageService.save(
                request.image(),
                StoragePath.forResource(
                        StorageResource.HEALTH_FACILITIES,
                        healthFacilityId));
        HealthFacilityImage healthFacilityImage = HealthFacilityImage.builder()
                .healthFacility(healthFacility)
                .path(path)
                .build();
        HealthFacilityImage savedHealthFacilityImage = repository.save(healthFacilityImage);
        return mapper.toResponse(savedHealthFacilityImage);
    }

    @Override
    public void delete(UUID healthFacilityId, HealthFacilityImageDeleteRequest request) {
        healthFacilityService.getByIdOrThrow(healthFacilityId);
        List<HealthFacilityImage> imagesToDelete = repository
                .findByHealthFacilityId(healthFacilityId).stream()
                .filter(image -> request.ids().contains(image.getId()))
                .toList();
        if (imagesToDelete.isEmpty()) {
            return;
        }
        repository.deleteAll(imagesToDelete);
        List<String> paths = imagesToDelete.stream()
                .map(HealthFacilityImage::getPath)
                .toList();
        eventPublisher.publishEvent(StorageDeletedEvent.builder()
                .paths(paths)
                .build());
    }

}
