package com.ddrissq.sigdosi.infrastructure.request.attachment.dto;

import java.util.UUID;

public record RequestAttachmentResponse(
        UUID id,
        String path
) {
}
