package com.ddrissq.sigdosi.infrastructure.request.attachment.service;

import com.ddrissq.sigdosi.common.exception.BusinessRuleException;
import com.ddrissq.sigdosi.common.file.storage.model.StorageDeletedEvent;
import com.ddrissq.sigdosi.common.file.storage.model.StoragePath;
import com.ddrissq.sigdosi.common.file.storage.model.StorageResource;
import com.ddrissq.sigdosi.common.file.storage.service.StorageService;
import com.ddrissq.sigdosi.infrastructure.request.attachment.configuration.RequestAttachmentProperties;
import com.ddrissq.sigdosi.infrastructure.request.attachment.dto.RequestAttachmentCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.attachment.dto.RequestAttachmentDeleteRequest;
import com.ddrissq.sigdosi.infrastructure.request.attachment.dto.RequestAttachmentResponse;
import com.ddrissq.sigdosi.infrastructure.request.attachment.error.RequestAttachmentErrorDescriptor;
import com.ddrissq.sigdosi.infrastructure.request.attachment.mapper.RequestAttachmentMapper;
import com.ddrissq.sigdosi.infrastructure.request.attachment.model.RequestAttachment;
import com.ddrissq.sigdosi.infrastructure.request.attachment.repository.RequestAttachmentRepository;
import com.ddrissq.sigdosi.infrastructure.request.model.Request;
import com.ddrissq.sigdosi.infrastructure.request.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class RequestAttachmentServiceImpl implements RequestAttachmentService {

    private final RequestAttachmentRepository repository;
    private final RequestAttachmentMapper mapper;
    private final RequestService requestService;
    private final RequestAttachmentProperties props;
    private final StorageService storageService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public List<RequestAttachmentResponse> get(UUID requestId) {
        requestService.getByIdOrThrow(requestId);
        return repository.findByRequestId(requestId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public RequestAttachmentResponse create(UUID requestId, RequestAttachmentCreateRequest request) {
        Request targetRequest = requestService.getByIdOrThrow(requestId);
        int currentCount = repository.countByRequestId(requestId);
        if (currentCount > props.limit()) {
            throw new BusinessRuleException(
                    RequestAttachmentErrorDescriptor.LIMIT_EXCEEDED);
        }
        String path = storageService.save(request.attachment(), StoragePath.forResource(
                StorageResource.HEALTH_FACILITIES, targetRequest.getHealthFacility().getId())
                .subResource(StorageResource.REQUESTS, requestId));
        RequestAttachment requestAttachment = RequestAttachment.builder()
                .request(targetRequest)
                .path(path)
                .build();
        RequestAttachment savedRequestAttachment = repository.save(requestAttachment);
        return mapper.toResponse(savedRequestAttachment);
    }

    @Override
    public void delete(UUID requestId, RequestAttachmentDeleteRequest request) {
        requestService.getByIdOrThrow(requestId);
        List<RequestAttachment> attachmentsToDelete = repository.findByRequestId(requestId).stream()
                .filter(attachment -> request.ids().contains(attachment.getId()))
                .toList();
        if (attachmentsToDelete.isEmpty()) {
            return;
        }
        repository.deleteAll(attachmentsToDelete);
        List<String> paths = attachmentsToDelete.stream()
                .map(RequestAttachment::getPath)
                .toList();
        eventPublisher.publishEvent(StorageDeletedEvent.builder()
                .paths(paths)
                .build());
    }

}
