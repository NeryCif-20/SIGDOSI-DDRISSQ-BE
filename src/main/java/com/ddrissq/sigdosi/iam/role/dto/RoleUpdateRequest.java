package com.ddrissq.sigdosi.iam.role.dto;

import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RoleUpdateRequest(
        @NullableNotBlank
        @Size(min = 1, max = 30, message = ValidationError.SIZE)
        String name,
        @NullableNotBlank
        String description,
        List<@NotNull(message = ValidationError.REQUIRED) UUID> permissions
) {
}
