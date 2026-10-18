package com.ddrissq.sigdosi.infrastructure.request.comment.mapper;

import com.ddrissq.sigdosi.common.mapping.annotation.IgnoreBaseFields;
import com.ddrissq.sigdosi.common.mapping.util.StringFunctions;
import com.ddrissq.sigdosi.iam.user.mapper.UserMapper;
import com.ddrissq.sigdosi.infrastructure.request.comment.dto.RequestCommentCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.comment.dto.RequestCommentResponse;
import com.ddrissq.sigdosi.infrastructure.request.comment.dto.RequestCommentUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.request.comment.model.RequestComment;
import org.mapstruct.*;

@Mapper(uses = {StringFunctions.class, UserMapper.class})
public interface RequestCommentMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "request", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "content", source = "content", qualifiedByName = "sanitizeFormattingBlocksAndLinks")
    RequestComment toComment(RequestCommentCreateRequest request);

    RequestCommentResponse toResponse(RequestComment requestComment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @IgnoreBaseFields
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "request", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "content", source = "content", qualifiedByName = "sanitizeFormattingBlocksAndLinks")
    void updateComment(RequestCommentUpdateRequest request, @MappingTarget RequestComment requestComment);

}
