package com.ddrissq.sigdosi.infrastructure.request.comment.dto;

import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.Size;

public record RequestCommentUpdateRequest(
        @NullableNotBlank
        @Size(min = 1, max = 2000, message = ValidationError.SIZE)
        String content
) {
}
