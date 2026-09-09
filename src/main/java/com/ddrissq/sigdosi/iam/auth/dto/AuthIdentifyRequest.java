package com.ddrissq.sigdosi.iam.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuthIdentifyRequest(
        @NotBlank
        @Email
        String email
) {
}
