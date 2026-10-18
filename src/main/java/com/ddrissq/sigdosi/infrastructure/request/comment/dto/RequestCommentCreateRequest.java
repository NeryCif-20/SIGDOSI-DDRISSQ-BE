package com.ddrissq.sigdosi.infrastructure.request.comment.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RequestCommentCreateRequest(
        UUID parent,
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 2000, message = ValidationError.SIZE)
        String content
) {
}
