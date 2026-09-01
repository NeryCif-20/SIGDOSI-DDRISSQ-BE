package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.iam.user.model.UserStatus;
import lombok.Builder;

@Builder
public record UserSearchRequest(
        String q,
        UserStatus status
) {
}
