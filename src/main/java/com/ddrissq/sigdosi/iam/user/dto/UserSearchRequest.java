package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.iam.user.model.UserStatus;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record UserSearchRequest(
        String search,
        Set<UserStatus> statuses,
        Set<UUID> roles
) {
}
