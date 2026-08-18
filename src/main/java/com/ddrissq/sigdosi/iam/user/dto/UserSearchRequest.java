package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.iam.user.model.UserAccountStatus;
import lombok.Builder;

@Builder
public record UserSearchRequest(
        String email,
        UserAccountStatus status,
        String cui,
        String name,
        String phoneNumber
) {
}
