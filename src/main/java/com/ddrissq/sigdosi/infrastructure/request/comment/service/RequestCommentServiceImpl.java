package com.ddrissq.sigdosi.infrastructure.request.comment.service;

import com.ddrissq.sigdosi.common.exception.BusinessRuleException;
import com.ddrissq.sigdosi.common.exception.ResourceNotFoundException;
import com.ddrissq.sigdosi.iam.security.provider.CurrentUserProvider;
import com.ddrissq.sigdosi.iam.user.model.User;
import com.ddrissq.sigdosi.iam.user.service.UserService;
import com.ddrissq.sigdosi.infrastructure.request.comment.dto.RequestCommentCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.comment.dto.RequestCommentResponse;
import com.ddrissq.sigdosi.infrastructure.request.comment.dto.RequestCommentUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.request.comment.error.RequestCommentErrorDescriptor;
import com.ddrissq.sigdosi.infrastructure.request.comment.mapper.RequestCommentMapper;
import com.ddrissq.sigdosi.infrastructure.request.comment.model.RequestComment;
import com.ddrissq.sigdosi.infrastructure.request.comment.repository.RequestCommentRepository;
import com.ddrissq.sigdosi.infrastructure.request.model.Request;
import com.ddrissq.sigdosi.infrastructure.request.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class RequestCommentServiceImpl implements RequestCommentService {

    private final RequestCommentRepository repository;
    private final RequestCommentMapper mapper;
    private final RequestService requestService;
    private final UserService userService;
    private final CurrentUserProvider userProvider;

    @Override
    public List<RequestCommentResponse> getByRequestAndParentIsNull(UUID requestId) {
        requestService.getByIdOrThrow(requestId);
        return repository.findByParentIsNullAndRequestId(requestId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public RequestCommentResponse create(UUID requestId, RequestCommentCreateRequest request) {
        Request targetRequest = requestService.getByIdOrThrow(requestId);
        User user = userService.getByIdOrThrow(userProvider.getUserId());
        RequestComment requestComment = mapper.toComment(request);
        requestComment.setRequest(targetRequest);
        requestComment.setUser(user);
        if (request.parent() != null) {
            RequestComment parent = getByIdAndRequestIdOrThrow(request.parent(), requestId);
            requestComment.setParent(parent);
        }
        RequestComment savedComment = repository.save(requestComment);
        return mapper.toResponse(savedComment);
    }

    @Override
    public RequestCommentResponse update(UUID id, UUID requestId, RequestCommentUpdateRequest request) {
        requestService.getByIdOrThrow(requestId);
        RequestComment requestComment = getByIdAndRequestIdOrThrow(id, requestId);
        User user = userService.getByIdOrThrow(userProvider.getUserId());
        if (!user.getId().equals(requestComment.getUser().getId())) {
            throw new BusinessRuleException(
                    RequestCommentErrorDescriptor.UPDATE_FORBIDDEN);
        }
        mapper.updateComment(request, requestComment);
        return mapper.toResponse(requestComment);
    }

    @Override
    public void delete(UUID id, UUID requestId) {
        RequestComment requestComment = getByIdAndRequestIdOrThrow(id, requestId);
        User user = userService.getByIdOrThrow(userProvider.getUserId());
        if (!user.getId().equals(requestComment.getUser().getId())) {
            throw new BusinessRuleException(
                    RequestCommentErrorDescriptor.DELETE_FORBIDDEN);
        }
        requestComment.setDeleted(Boolean.TRUE);
    }

    @Override
    public List<RequestCommentResponse> getReplies(UUID id, UUID requestId) {
        getByIdAndRequestIdOrThrow(id, requestId);
        return repository.findByIdAndRequestId(id, requestId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    private RequestComment getByIdAndRequestIdOrThrow(UUID id, UUID requestId) {
        return repository.findByIdAndRequestId(id, requestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        RequestCommentErrorDescriptor.NOT_FOUND));
    }

}
