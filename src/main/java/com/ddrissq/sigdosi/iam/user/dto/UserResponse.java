package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.iam.user.model.UserAccountStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(
        UUID id,
        String email,
        UserAccountStatus status,
        String cui,
        String firstName,
        String lastName,
        String avatar,
        String phoneNumber
) {
}
