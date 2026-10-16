package com.ddrissq.sigdosi.infrastructure.request.attachment.mapper;

import com.ddrissq.sigdosi.infrastructure.request.attachment.dto.RequestAttachmentResponse;
import com.ddrissq.sigdosi.infrastructure.request.attachment.model.RequestAttachment;
import org.mapstruct.Mapper;

@Mapper
public interface RequestAttachmentMapper {

    RequestAttachmentResponse toResponse(RequestAttachment requestAttachment);

}
