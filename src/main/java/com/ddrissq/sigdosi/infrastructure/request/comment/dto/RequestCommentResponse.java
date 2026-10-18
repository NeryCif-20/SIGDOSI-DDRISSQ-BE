package com.ddrissq.sigdosi.infrastructure.request.comment.dto;

import com.ddrissq.sigdosi.iam.user.dto.UserResponse;

import java.time.Instant;

public record RequestCommentResponse(
        UserResponse user,
        String content,
        Instant updatedAt
) {
}
